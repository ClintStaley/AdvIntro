package com.cstaley.stm3911.MMind.V2.ans;

import java.util.*;

/**
 * CountingModel extends SimpleModel and tracks all patterns that are
 * consistent with responses thus far. Reports the count and sample
 * pattern in toString rather than the actual pattern.
 */
public class CountingModel extends SimpleModel {
   private List<String> viablePatterns;
   private final int length;
   private final char maxChar;
   
   public CountingModel(int length, char maxChar, Random rnd) {
      super(length, maxChar, rnd);
      this.length = length;
      this.maxChar = maxChar;
      this.viablePatterns = generateAllPatterns(length, maxChar);
   }
   
   /**
    * Generate all patterns in alphabetical order.
    */
   private static List<String> generateAllPatterns(int length, 
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
   
   /**
    * Compute matches between a pattern string and a guess.
    */
   private Matches computeMatches(String patternStr, Guess guess) {
      int exact = 0, inexact = 0;
      boolean[] ptnUsed = new boolean[length];
      boolean[] guessUsed = new boolean[length];

      for (int idx = 0; idx < length; idx++) {
         if (patternStr.charAt(idx) == guess.getChar(idx)) {
            ptnUsed[idx] = guessUsed[idx] = true;
            exact++;
         }
      }
      
      for (int idx = 0; idx < length; idx++)
         for (int otherIdx = 0; otherIdx < length; otherIdx++)
            if (!ptnUsed[idx] && !guessUsed[otherIdx] 
               && patternStr.charAt(idx) == 
                  guess.getChar(otherIdx)) {
               ptnUsed[idx] = guessUsed[otherIdx] = true;
               inexact++;
            }

      return new Matches(exact, inexact);
   }
   
   @Override
   public Matches getMatches(Guess toCheck) {
      // Get the actual response from the parent pattern
      Matches actualResponse = super.getMatches(toCheck);
      
      // Filter viable patterns to only those consistent with response
      Matches response = actualResponse;
      viablePatterns.removeIf(p -> 
         !computeMatches(p, toCheck).equals(response));
      
      // Sort to maintain alphabetical order
      Collections.sort(viablePatterns);
      
      return actualResponse;
   }
   
   @Override
   public String toString() {
      return "" + viablePatterns.size();
   }
}
