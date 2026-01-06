package com.cstaley.stm3911.AI;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolvePuzzle {
   public static void main(String[] args) {
      int maxCost = -1, numSlns = 0;
      String pName = "<None>", sName = "<None>";
      Scanner in = new Scanner(System.in);
      Solver solver = null;
      Problem problem = null;
      Solver.Solution[] answers;
      Solver.Solution ans;

      Logger.getLogger("global").setLevel
         (args.length > 0 ? Level.INFO : Level.INFO);
      while (solver == null || problem == null) {
         System.out.print("Enter problem type, solution type, max cost and"
            + " max # of solutions: ");
         try {
            pName = in.next();
            sName = in.next();
            maxCost = in.nextInt();
            numSlns = in.nextInt();
            problem = (Problem)Class.forName(pName).getConstructor()
               .newInstance();
            solver = (Solver)Class.forName(sName).getConstructor().newInstance();
         }
         catch (Exception err) {
            if (problem == null)
               System.out.println(pName + " isn't a valid problem class.");
            if (solver == null)
               System.out.println(sName + " isn't a valid solver class.");
            if (maxCost == -1)
               System.out.println("Please enter a nonnegative integer " + 
                  "for max cost.");
            if (numSlns == 0)
               System.out.println("Please enter a positive integer " + 
                  "for number of solutions.");
         }
      }
      try {
         System.out.println("Enter problem: ");
         problem.read(in);
         answers = solver.solveProblem(problem, maxCost, numSlns);
         if (answers.length == 0)
            System.out.println("No solution exists.");
         else {
            System.out.println("Answers are: ");
            for (int ndx = 0; ndx < answers.length; ndx++) {
               ans = answers[ndx];
               System.out.println("Answer " + ndx + " with cost " + ans.mCost);
               for (Problem.Step step : ans.mSteps)
                  System.out.println("   " + step);
            }
         }
      }
      catch (Exception err) {
         System.out.println("Read error: " + err.getMessage());
         err.printStackTrace();
      }
   }
}

/*
 * 
 * com.cstaley.csci182.AI.Answer.GemPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 6 1
 * 4 4
 * 1 2 3 4
 * 5 6 7 8
 * 9 10 15 11
 * 13 0 14 12
 * 
 * com.cstaley.csci182.AI.Answer.GemPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 4 2
 * 4 4
 * 1 2 3 4
 * 6 7 0 8
 * 5 10 15 11
 * 9 13 14 12
 * 
 * com.cstaley.csci182.AI.Answer.GemPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 4 2
 * 4 4
 * 1 2 3 4
 * 6 7 0 8
 * 5 10 15 11
 * 9 13 14 12
 * 
 * 
 * com.cstaley.csci182.AI.Answer.SudokuPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 80 2
 * 5 3 0 0 7 0 0 0 0
 * 6 0 0 1 9 5 0 0 0
 * 0 9 8 0 0 0 0 6 0
 * 8 0 0 0 0 0 0 0 3
 * 4 0 0 8 0 3 0 0 1
 * 7 0 0 0 2 0 0 0 6
 * 0 6 0 0 0 0 2 8 0
 * 0 0 0 4 1 9 0 0 5
 * 0 0 0 0 8 0 0 7 9
 * 
 * com.cstaley.csci182.AI.Answer.SudokuPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 100 2
 * 9 8 0 7 0 0 3 0 0
 * 0 0 0 0 8 0 0 0 6
 * 0 0 0 0 5 9 2 7 0
 * 0 7 0 0 0 5 0 0 0
 * 4 0 9 0 0 0 8 0 1
 * 0 6 0 2 0 8 0 0 3
 * 0 0 0 0 0 0 0 0 4
 * 0 0 0 9 0 6 0 0 0
 * 1 0 6 0 2 4 5 0 7
 * 
 * com.cstaley.csci182.AI.Answer.SudokuPuzzle
 * com.cstaley.csci182.AI.Answer.DFSSolver 81 2
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 
 */

/*
com.cstaley.csci182.AI.Answer.BinPacker
com.cstaley.csci182.AI.BnBSolver 20 2
 8
 6 1
 4 1
 3 1
 2 2
 2 6 
 2 1
*/