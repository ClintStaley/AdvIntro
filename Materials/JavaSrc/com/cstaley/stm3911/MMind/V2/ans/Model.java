package com.cstaley.stm3911.MMind.V2.ans;

import java.util.Random;

public class Model {
   static record Matches(int exact, int inexact) {}
   
   public char[] pattern;
   
   public Model(int length, char maxChar, Random rnd) {
      pattern = new char[length];
      for (int index = 0; index < pattern.length; index++)
         pattern[index] = (char)('A' + rnd.nextInt(maxChar - 'A' + 1));
   }
   
   public Matches findMatches(Guess toCheck) {
      int exact = 0, inexact = 0, idx, otherIdx;
      boolean[] myUsed = new boolean[pattern.length];
      boolean[] otherUsed = new boolean[pattern.length];

      for (idx = 0; idx < pattern.length; idx++) {
         if (pattern[idx] == toCheck.getChar(idx)) {
            myUsed[idx] = otherUsed[idx] = true;
            exact++;
         }
      }
      
      for (idx = 0; idx < pattern.length; idx++)
         for (otherIdx = 0; otherIdx < pattern.length; otherIdx++)
            if (!myUsed[idx] && !otherUsed[otherIdx] 
               && pattern[idx] == toCheck.getChar(otherIdx)) {
               myUsed[idx] = otherUsed[otherIdx] = true;
               inexact++;
            }

      return new Matches(exact, inexact);
   }
   
   public String toString() {return new String(pattern);}
}