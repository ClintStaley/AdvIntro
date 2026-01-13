package com.cstaley.stm3911.MMind.V2.ans;

import java.util.*;

/*
This Model plays a Liars version of Mastermind. It does not set a fixed 
pattern, but instead keeps track of which patterns are consistent with 
the guesses and responses so far. When a guess is made, it returns the 
response that leaves the most viable patterns.
*/
public class LiarsModel {
   static record Matches(int exact, int inexact) {}
   
   private List<String> viablePatterns;
   private final int length;
   
   public LiarsModel(int length, char maxChar) {
      this.length = length;
      this.viablePatterns = generateAllPatterns(length, maxChar);
   }
   
   /*
    * Generate all patterns in alphabetical order (AAAA, AAAB, AAAC, ...).
    * By processing positions from most significant to least significant,
    * patterns are naturally generated in lexicographic order.
    */
   private static List<String> generateAllPatterns(int length, char maxChar) {
      List<String> result = new ArrayList<>();
      int base = maxChar - 'A' + 1;
      int total = (int) Math.pow(base, length);
      
      for (int i = 0; i < total; i++) {
         char[] pattern = new char[length];
         int value = i;
         // Process from most significant position to least significant
         // This ensures alphabetical ordering: AAAA, AAAB, AAAC, ...
         for (int pos = length - 1; pos >= 0; pos--) {
            pattern[pos] = (char)('A' + value % base);
            value /= base;
         }
         result.add(new String(pattern));
      }
      return result;
   }
   
   private Matches computeMatches(String pattern, Guess guess) {
      int exact = 0, inexact = 0;
      boolean[] ptnUsed = new boolean[length];
      boolean[] guessUsed = new boolean[length];

      for (int idx = 0; idx < length; idx++) {
         if (pattern.charAt(idx) == guess.getChar(idx)) {
            ptnUsed[idx] = guessUsed[idx] = true;
            exact++;
         }
      }
      
      for (int idx = 0; idx < length; idx++)
         for (int otherIdx = 0; otherIdx < length; otherIdx++)
            if (!ptnUsed[idx] && !guessUsed[otherIdx] 
               && pattern.charAt(idx) == guess.getChar(otherIdx)) {
               ptnUsed[idx] = guessUsed[otherIdx] = true;
               inexact++;
            }

      return new Matches(exact, inexact);
   }
   
   /*
    * When multiple response groups have the same maximum count, we break ties
    * deterministically: first by smallest number of exact matches, then by
    * smallest number of inexact matches. This ordering offers the least
    * "psychological" information while maintaining determinism.
    */
   public Matches findMatches(Guess toCheck) {
      Map<Matches, List<String>> groups = new HashMap<>();
      
      for (String pattern : viablePatterns) {
         Matches response = computeMatches(pattern, toCheck);
         groups.computeIfAbsent(response, k -> new ArrayList<>()).add(pattern);
      }
      
      Matches bestResponse = null;
      int maxCount = 0;
      for (var entry : groups.entrySet()) {
         int count = entry.getValue().size();
         Matches response = entry.getKey();
         
         // Prefer response with larger count
         // On ties, prefer smallest exact matches, then smallest inexact matches
         if (count > maxCount || 
            (count == maxCount && bestResponse != null && 
            (response.exact() < bestResponse.exact() ||
            (response.exact() == bestResponse.exact() && 
            response.inexact() < bestResponse.inexact())))) {
            
            maxCount = count;
            bestResponse = response;
         }
      }
      
      viablePatterns = groups.get(bestResponse);
      // Patterns remain in alphabetical order since:
      // 1. We iterate viablePatterns in order (which is alphabetical)
      // 2. We add patterns to map lists in that same order
      // 3. So the map value lists maintain alphabetical order
      // No sorting needed!
      
      return bestResponse;
   }
   
   public int getViableCount() { return viablePatterns.size(); }
   
   /*
    * Returns the alphabetically lowest pattern from the remaining viable set.
    * The viable patterns list is maintained in alphabetical order throughout
    * (generated in order, and order is preserved when filtering), so this
    * always returns the lexicographically smallest pattern (the first one).
    */
   public String getAlphabeticallyLowest() {
      if (viablePatterns.isEmpty())
         return null;
      // List is already in alphabetical order, no sorting needed
      return viablePatterns.get(0);
   }
   
   // Return number of viable patterns, and the alphabetically lowest pattern
   public String toString() {
      if (viablePatterns.isEmpty())
         return "0 viable patterns";
      // List is already in alphabetical order, no sorting needed
      return viablePatterns.size() + " viable patterns: " + viablePatterns.get(0);
   }
}
