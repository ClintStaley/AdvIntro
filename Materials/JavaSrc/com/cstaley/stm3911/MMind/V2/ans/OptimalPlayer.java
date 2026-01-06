package com.cstaley.stm3911.MMind.V2.ans;

import java.util.*;

/*
An optimal Mastermind player that chooses guesses to minimize expected 
remaining viable patterns (sum-of-squares metric). Searches all patterns,
not just viable ones, since non-viable guesses can sometimes partition
the viable set more evenly.
*/
public class OptimalPlayer {
   static record Matches(int exact, int inexact) {}
   
   private List<String> viablePatterns;
   private final List<String> allPatterns;
   private final int length;
   
   public OptimalPlayer(int length, char maxChar) {
      this.length = length;
      this.allPatterns = generateAllPatterns(length, maxChar);
      this.viablePatterns = new ArrayList<>(allPatterns);
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
   
   private Matches computeMatches(String pattern, String guess) {
      int exact = 0, inexact = 0;
      boolean[] ptnUsed = new boolean[length];
      boolean[] guessUsed = new boolean[length];

      for (int idx = 0; idx < length; idx++) {
         if (pattern.charAt(idx) == guess.charAt(idx)) {
            ptnUsed[idx] = guessUsed[idx] = true;
            exact++;
         }
      }
      
      for (int idx = 0; idx < length; idx++)
         for (int otherIdx = 0; otherIdx < length; otherIdx++)
            if (!ptnUsed[idx] && !guessUsed[otherIdx] 
               && pattern.charAt(idx) == guess.charAt(otherIdx)) {
               ptnUsed[idx] = guessUsed[otherIdx] = true;
               inexact++;
            }

      return new Matches(exact, inexact);
   }
   
   /*
   Score a guess by sum of squares of partition sizes.
   Lower is better: means more even partitioning of viable patterns.
   */
   private int scoreGuess(String guess) {
      Map<Matches, Integer> counts = new HashMap<>();
      for (String pattern : viablePatterns) {
         Matches response = computeMatches(pattern, guess);
         counts.merge(response, 1, Integer::sum);
      }
      
      int sumOfSquares = 0;
      for (int count : counts.values())
         sumOfSquares += count * count;
      return sumOfSquares;
   }
   
   /*
   Find the best guess: lowest sum-of-squares score.
   Ties broken in favor of viable patterns (can win immediately).
   */
   public String findBestGuess() {
      if (viablePatterns.size() == 1)
         return viablePatterns.get(0);
      
      String best = null;
      int bestScore = Integer.MAX_VALUE;
      boolean bestIsViable = false;
      
      for (String candidate : allPatterns) {
         int score = scoreGuess(candidate);
         boolean isViable = viablePatterns.contains(candidate);
         
         if (score < bestScore || 
             (score == bestScore && isViable && !bestIsViable)) {
            bestScore = score;
            best = candidate;
            bestIsViable = isViable;
         }
      }
      return best;
   }
   
   /*
   After making a guess and receiving a response, update the viable set
   to only those patterns consistent with this response.
   */
   public void recordResponse(String guess, Matches response) {
      viablePatterns.removeIf(p -> !computeMatches(p, guess).equals(response));
   }
   
   public int viableCount() { 
      return viablePatterns.size(); 
   }
   
   public boolean isSolved() {
      return viablePatterns.size() == 1;
   }
   
   public String getSolution() {
      return viablePatterns.size() == 1 ? viablePatterns.get(0) : null;
   }
}

