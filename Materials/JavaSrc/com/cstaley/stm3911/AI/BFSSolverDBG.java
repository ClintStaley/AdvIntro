package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Queues.LinkQueue;
import com.cstaley.stm3911.Queues.Queue;
import com.cstaley.stm3911.Stacks3.LinkStack;
import com.cstaley.stm3911.Stacks3.Stack;

public class BFSSolverDBG implements Solver {
   static private class BFSStep {
      private Problem.Step mStep;
      private Problem.State mState;
      
      private BFSStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
      }
      
      public boolean equals(Object rhs) {
         BFSStep sRhs = (BFSStep)rhs;
         
         return mState.equals(sRhs.mState);
      }
   }

   // 1 -- Add a stack only if expansion is still possible on it.
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      List<Solution> slns = new LinkedList<Solution>();
      Solution sln;
      LinkStack<BFSStep> newStack = null, stack = new LinkStack<BFSStep>();
      Queue<Stack<BFSStep>> queue = new LinkQueue<Stack<BFSStep>>();
      Problem.Step step;
      Problem.State topState, newState;
      BFSStep newStep;
      Logger log = Logger.getLogger("global");
            
      stack.push(new BFSStep(prb.getStartState(), null));
      queue.add(stack);
      
bigLoop:
      while (!queue.isEmpty()) {
         stack = (LinkStack<BFSStep>)queue.getFront();
         topState = stack.top().mState;

         log.log(Level.INFO, "Expanding on stack ending with\n" + topState);
         for (Iterator<Problem.Step> itr = topState.iterator(); itr.hasNext();){
            step = itr.next();
            log.log(Level.INFO, "Trying step " + step);
            newState = topState.applyStep(step);
            newStep = new BFSStep(newState, step);
            if (!stack.isOn(newStep)) {
               newStack = new LinkStack<BFSStep>(stack);
               newStack.push(newStep);
               if (prb.getDistance(newState) == 0) {
                  log.log(Level.INFO, "New solution");
                  sln = new Solution();
                  sln.mCost = newStack.getSize() - 1;
                  sln.mSteps = new Problem.Step[sln.mCost];
                  for (int ndx = sln.mSteps.length-1; ndx >= 0; ndx--) {
                     sln.mSteps[ndx] = newStack.top().mStep;
                     newStack.pop();
                  }
                  slns.add(sln);
                  if (slns.size() >= numSlns)
                     break bigLoop;
               }
               else if (newStack.getSize() - 1 < maxCost) { // 1
                  log.log(Level.INFO, "Good step, taking it.");
                  queue.add(newStack);
               }
            }
         }   
         queue.remove();
      }
      return slns.toArray(new Solution[0]);      
   }
}