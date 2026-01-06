package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Stacks3.ArrStack;

public class BnBSolver implements Solver {
   
   // One step in a chain leading from starting state to a solution
   static private class BnBStep {
      private Problem.State mState;        // State along the path
      private Problem.Step mStep;          // Step that *arrives* at the state
      private Iterator<Problem.Step> mItr; // Itr through steps *leaving* state
      
      private BnBStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
         mItr = state.iterator();
      }
      
      public boolean equals(Object rhs) {
         BnBStep sRhs = (BnBStep)rhs;
         
         return mState.equals(sRhs.mState);
      }
   }

   @Override
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      List<Solution> slns = new LinkedList<Solution>();
      Solution sln;
      ArrStack<BnBStep> stack = new ArrStack<BnBStep>(), cpyStack;
      int currentCost = 0;
      int bestCost = Integer.MAX_VALUE;
      BnBStep top = null;
      Problem.Step step;
      Problem.State newState;
      Logger log = Logger.getLogger("global");
      
      stack.push(new BnBStep(prb.getStartState(), null));
 
      while (!stack.isEmpty()) {
         top = stack.top();                // Current state/step in DFS
         
         // If we can move forward (without repetition)
         if (top.mItr.hasNext()) {
            step = top.mItr.next();
            newState = top.mState.applyStep(step);
            
            // If the best possible cost for the current path is less than 
            // the best cost found so far, continue the search.
            if (currentCost + step.getCost() + newState.getDistance() < bestCost) {
               log.log(Level.INFO, "Taking step " + step);

               stack.push(new BnBStep(newState, step));
               currentCost += step.getCost();

               if (newState.isGoal()) {  // If solution
                  log.log(Level.INFO, "Making solution with state\n" + newState);

                  cpyStack = new ArrStack<BnBStep>(stack);
                  sln = new Solution();
                  sln.mCost = bestCost = currentCost;
                     
                  sln.mSteps = new Problem.Step[stack.getSize()];
                  
                  for (int ndx = sln.mSteps.length - 1; ndx >= 0; ndx--) {
                     sln.mSteps[ndx] = cpyStack.top().mStep;
                     cpyStack.pop();
                  }
                  slns.add(sln);
                  stack.pop();
               }
            }
            else {
               // Log the skipped step and the relative cost to the best cost.
               log.log(Level.INFO, "Skipping step " + step + " with cost "
                  + step.getCost()
                  + " and relative cost " + (step.getCost() 
                  + newState.getDistance() - bestCost));
            }
         }
         else {
            stack.pop();
            log.log(Level.INFO, "Backtracking");
         }
      }
      return slns.toArray(new Solution[0]);
   }
}