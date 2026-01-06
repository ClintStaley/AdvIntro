package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Stacks3.ArrStack;

public class DFSSolver implements Solver {
   
   // One step in a chain leading from starting state to a solution
   static private class DFSStep {
      private Problem.State mState;        // State along the path
      private Problem.Step mStep;          // Step that *arrives* at the state
      private Iterator<Problem.Step> mItr; // Iterator through steps *leaving* state
      
      private DFSStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
         mItr = state.iterator();
      }
      
      public boolean equals(Object rhs) {
         DFSStep sRhs = (DFSStep)rhs;
         
         return mState.equals(sRhs.mState);
      }
   }

   @Override
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      List<Solution> slns = new LinkedList<Solution>();
      Solution sln;
      ArrStack<DFSStep> stack = new ArrStack<DFSStep>(), cpyStack;
      DFSStep top = null;
      Problem.Step step;
      Problem.State newState;
      DFSStep newStep;
      Logger log = Logger.getLogger("global");
      
      stack.push(new DFSStep(prb.getStartState(), null));
 
      while (!stack.isEmpty()) {
         top = stack.top();                // Current state/step in DFS
         
         // If we can move forward (without repetition)
         if (top.mItr.hasNext()) {
            step = top.mItr.next();
            newState = top.mState.applyStep(step);
            
            newStep = new DFSStep(newState, step);
            
            if (!stack.isOn(newStep)) {
               stack.push(newStep);
               log.log(Level.INFO, "Taking step " + step);
               
               if (newState.isGoal()) {  // If solution
                  log.log(Level.INFO, "Making solution");
                  cpyStack = new ArrStack<DFSStep>(stack);
                  sln = new Solution();
                  sln.mCost = 0;
                     
                  sln.mSteps = new Problem.Step[stack.getSize()];
                  
                  for (int ndx = sln.mSteps.length - 1; ndx >= 0; ndx--) {
                     sln.mSteps[ndx] = cpyStack.top().mStep;
                     if (ndx > 0)
                        sln.mCost += cpyStack.top().mStep.getCost();
                     cpyStack.pop();
                  }
                  slns.add(sln);
                  stack.pop();
               }
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