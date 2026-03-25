package com.cstaley.stm3911.MMind.V2.ans;

import java.util.Random;

/**
 * Simple Model implementation that maintains a fixed pattern and returns
 * matches based on that pattern.
 */
public class SimpleModel implements Model {
   public char[] pattern;
   
   public SimpleModel(int length, char maxChar, Random rnd) {
      pattern = new char[length];
      for (int index = 0; index < pattern.length; index++)
         pattern[index] = (char)('A' + 
            rnd.nextInt(maxChar - 'A' + 1));
   }
   
   /**
    * Compute matches between a pattern and a guess.
    */
   protected Matches computeMatches(char[] pat, Guess guess) {
      int exact = 0, inexact = 0;
      boolean[] ptnUsed = new boolean[pat.length];
      boolean[] guessUsed = new boolean[pat.length];

      for (int idx = 0; idx < pat.length; idx++) {
         if (pat[idx] == guess.getChar(idx)) {
            ptnUsed[idx] = guessUsed[idx] = true;
            exact++;
         }
      }
      
      for (int idx = 0; idx < pat.length; idx++)
         for (int otherIdx = 0; otherIdx < pat.length; otherIdx++)
            if (!ptnUsed[idx] && !guessUsed[otherIdx] 
               && pat[idx] == guess.getChar(otherIdx)) {
               ptnUsed[idx] = guessUsed[otherIdx] = true;
               inexact++;
            }

      return new Matches(exact, inexact);
   }
   
   @Override
   public Matches getMatches(Guess toCheck) {
      return computeMatches(pattern, toCheck);
   }
   
   @Override
   public String toString() {
      return new String(pattern);
   }
}
