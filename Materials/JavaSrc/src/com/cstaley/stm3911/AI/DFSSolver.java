package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DFSSolver implements Solver {
   
   // One step in a chain leading from starting state to a solution
   static private class DFSStep implements Problem.Step {
      private Problem.State mState;        // State along the path
      private Problem.Step mStep;          // Step that *arrives* at the state
      private Iterator<Problem.Step> mItr; // Iterator through steps *leaving* state
      
      private DFSStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
         mItr = state.iterator();
      }
      
      public int getCost() {return mStep == null ? 0 : mStep.getCost();}
      
      public boolean equals(Object rhs) {
         DFSStep sRhs = (DFSStep)rhs;
         
         return mState.equals(sRhs.mState);
      }

      public String toString() {
         return mStep == null ? "start" : mStep.toString();
      }
   }

   @Override
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      List<Solution> slns = new LinkedList<Solution>();
      StepStack stack = new StepStack();
      DFSStep top = null;
      Problem.Step step;
      Problem.State newState;
      DFSStep newStep;
      Logger log = Logger.getLogger("global");
      
      stack.push(new DFSStep(prb.getStartState(), null));

      while (!stack.isEmpty() && slns.size() < numSlns) {
         top = (DFSStep)stack.top();             // Current state/step in DFS
         
         // If we can move forward (without repetition)
         if (top.mItr.hasNext()) {
            step = top.mItr.next();
            newState = top.mState.applyStep(step);
            
            newStep = new DFSStep(newState, step);
            
            if (!stack.isOn(newStep)) {
               stack.push(newStep);
               log.log(Level.INFO, "Taking step " + step);
               
               if (newState.isGoal()) {  
                  log.log(Level.INFO, "Making solution");
                  slns.add(stack.makeSolution());
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
