package com.cstaley.stm3911.MMind.V2.ans;

import java.util.*;

/*
This Model plays a Liars version of Mastermind. It does not set a fixed 
pattern, but instead keeps track of which patterns are consistent with 
the guesses and responses so far. When a guess is made, it returns the 
response that leaves the most viable patterns.
*/
public class LiarsModel extends CountingModel {
   
   public LiarsModel(int length, char maxChar) {
      super(length, maxChar, new Random());
   }
   
   /*
    * When multiple response groups have the same maximum count, we break ties
    * deterministically: first by smallest number of exact matches, then by
    * smallest number of inexact matches. This ordering offers the least
    * "psychological" information while maintaining determinism.
    */
   @Override
   public Matches getMatches(Guess toCheck) {
      Map<Matches, List<String>> groups = new HashMap<>();
      
      for (String pattern : viablePatterns) {
         char[] patArray = pattern.toCharArray();
         Matches response = computeMatches(patArray, toCheck);
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

   public int getViableCount() {
      return viablePatterns.size();
   }
   
   @Override
   public String toString() {
      if (viablePatterns.isEmpty())
         return "0 viable patterns";
      return viablePatterns.size() + " viable patterns: " + viablePatterns.get(0);
   }
}
