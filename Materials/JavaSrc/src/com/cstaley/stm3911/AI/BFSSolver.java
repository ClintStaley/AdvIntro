package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Queues.LinkQueue;
import com.cstaley.stm3911.Queues.Queue;

public class BFSSolver implements Solver {
   static private class BFSStep implements Problem.Step {
      private Problem.Step mStep;
      private Problem.State mState;
      
      private BFSStep(Problem.State state, Problem.Step step) {
         mState = state;
         mStep = step;
      }
      
      public int getCost() {return mStep == null ? 0 : mStep.getCost();}
      
      public boolean equals(Object rhs) {
         BFSStep sRhs = (BFSStep)rhs;
         
         return mState.equals(sRhs.mState);
      }
   }

   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      List<Solution> slns = new LinkedList<Solution>();
      StepStack newStack = null, stack = new StepStack();
      Queue<StepStack> queue = new LinkQueue<StepStack>();
      Problem.Step step;
      Problem.State topState, newState;
      BFSStep newStep;
      Logger log = Logger.getLogger("global");
            
      stack.push(new BFSStep(prb.getStartState(), null));
      queue.add(stack);
      
      while (!queue.isEmpty()) {
         stack = queue.getFront();
         topState = ((BFSStep)stack.top()).mState;

         log.log(Level.INFO, "Expanding on stack ending with\n" + topState);
         for (Iterator<Problem.Step> itr = topState.iterator(); itr.hasNext();){
            step = itr.next();
            log.log(Level.INFO, "Trying step " + step);
            newState = topState.applyStep(step);
            newStep = new BFSStep(newState, step);
            if (!stack.isOn(newStep)) {
               newStack = new StepStack(stack);
               newStack.push(newStep);
               if (newState.isGoal()) {
                  log.log(Level.INFO, "New solution");
                  slns.add(newStack.makeSolution());
               }
               else {
                  log.log(Level.INFO, "Taking step");
                  queue.add(newStack);
               }
            }
         }   
         queue.remove();
      }
      return slns.toArray(new Solution[0]);      
   }
}
