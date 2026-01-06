package com.cstaley.stm3911.AI;

// A Solver solves a Problem, using some AI state-traversal method.
// All implementing classes are expected to have a default constructor.
public interface Solver {
   
   // A Solver produces Solutions, each of which is a series of Steps (see
   // Problem) leading from the Problem's starting State to a solution State, e.g.
   // a series of moves in a maze, or piece-slides in a Gem puzzle.
   public static class Solution {
      public Problem.Step[] mSteps;  // The series of Steps
      public int            mCost;   // Total cost of all Steps in the Solution
   }

   // Solve "prb", returning zero or more Solutions (zero if the Problem is 
   // insoluble).  Limit yourself to solutions with cost at most "maxCost".  
   // This lets the solver cull possible Solutions that are getting too long, 
   // and may be a blind alley.  Also, cut off the search once you've found 
   // "numSlns" different Solutions, since some Problems may have a huge number
   // of Solutions.
   //
   // Assume "prb" is a valid Problem, and that "maxCost" and "numSlns" are
   // positive.
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns);
}