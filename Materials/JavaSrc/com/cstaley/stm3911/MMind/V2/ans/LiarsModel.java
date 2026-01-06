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
   
   private static List<String> generateAllPatterns(int length, char maxChar) {
      List<String> result = new ArrayList<>();
      int base = maxChar - 'A' + 1;
      int total = (int) Math.pow(base, length);
      
      for (int i = 0; i < total; i++) {
         char[] pattern = new char[length];
         int value = i;
         for (int pos = 0; pos < length; pos++) {
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
   
   public Matches findMatches(Guess toCheck) {
      Map<Matches, List<String>> groups = new HashMap<>();
      
      for (String pattern : viablePatterns) {
         Matches response = computeMatches(pattern, toCheck);
         groups.computeIfAbsent(response, k -> new ArrayList<>()).add(pattern);
      }
      
      Matches bestResponse = null;
      int maxCount = 0;
      for (var entry : groups.entrySet()) {
         if (entry.getValue().size() > maxCount) {
            maxCount = entry.getValue().size();
            bestResponse = entry.getKey();
         }
      }
      
      viablePatterns = groups.get(bestResponse);
      return bestResponse;
   }
   
   public int viableCount() { return viablePatterns.size(); }
   
   public String toString() {
      return viablePatterns.size() == 1 ? viablePatterns.get(0) : "?";
   }
}
