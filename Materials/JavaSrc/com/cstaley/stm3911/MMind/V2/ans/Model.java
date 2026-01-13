package com.cstaley.stm3911.MMind.V2.ans;

/**
 * Interface for Mastermind model implementations.
 */
public interface Model {
   /**
    * Compute the matches (exact and inexact) for a given guess.
    * @param toCheck the guess to check against the model's pattern
    * @return a Matches record with exact and inexact counts
    */
   Matches getMatches(Guess toCheck);
   
   static record Matches(int exact, int inexact) {}
}
