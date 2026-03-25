package com.cstaley.stm3911.MMind.V2.ans;

import java.util.*;

/**
 * CountingModel extends SimpleModel and tracks all patterns that are
 * consistent with responses thus far. Reports the count and sample
 * pattern in toString rather than the actual pattern.
 */
public class CountingModel extends SimpleModel {
   protected List<String> viablePatterns;
   
   public CountingModel(int length, char maxChar, Random rnd) {
      super(length, maxChar, rnd);
      this.viablePatterns = generateAllPatterns(length, maxChar);
   }
   
   /**
    * Generate all patterns in alphabetical order.
    */
   protected static List<String> generateAllPatterns(int length, 
         char maxChar) {
      List<String> result = new ArrayList<>();
      int base = maxChar - 'A' + 1;
      int total = (int) Math.pow(base, length);
      
      for (int i = 0; i < total; i++) {
         char[] pattern = new char[length];
         int value = i;
         // Process from most significant position to least significant
         for (int pos = length - 1; pos >= 0; pos--) {
            pattern[pos] = (char)('A' + value % base);
            value /= base;
         }
         result.add(new String(pattern));
      }
      return result;
   }
   
   @Override
   public Matches getMatches(Guess toCheck) {
      // Get the actual response from the parent pattern
      Matches actualResponse = super.getMatches(toCheck);
      
      // Filter viable patterns to only those consistent with response
      Matches response = actualResponse;
      
      viablePatterns.removeIf(p -> {
         char[] patArray = p.toCharArray();
         return !computeMatches(patArray, toCheck).equals(response);
      });
      
      return actualResponse;
   }
   
   @Override
   public String toString() {
      return "" + viablePatterns.size();
   }
}
