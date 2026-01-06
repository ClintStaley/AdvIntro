package com.cstaley.stm3911.AI;

import java.util.Comparator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Queues.Queue;
import com.cstaley.stm3911.Queues.Answer.FullHeapPQueue;
import com.cstaley.stm3911.Stacks3.LinkStack;

public class AStarSolver implements Solver {
   static private class AStarStep {
      public Problem.Step mStep;
      public Problem.State mState;
      
      public AStarStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
      };
      
      public boolean equals(Object rhs) {
         AStarStep sRhs = (AStarStep)rhs;
         
         return mState.equals(sRhs.mState);
      }
   }
   
   static private class AStarPath extends LinkStack<AStarStep> {
      private int mHeadCost; // Cost of state at the TOS
      private int mCost;     // Total path cost: step costs plus mHeadCost
      private Problem mPrb;
      
      public AStarPath(Problem prb) {mPrb = prb;}
      
      public int getCost() {return mCost;}
      
      public void push(AStarStep step) {
         int newHeadCost = mPrb.getDistance(step.mState);
         
         mCost += newHeadCost - mHeadCost; // Update mCost component due to TOS
         if (step.mStep != null)
            mCost += step.mStep.getCost(); // Add new step cost if not 1st state
         mHeadCost = newHeadCost;
         super.push(step);
      }
      
      public void pop(Problem prb) {
         assert false; // No popping in AStar.
      }
   }

   // 1 -- Add a stack only if expansion is still possible on it.
   public Solution[] solveProblem(Problem prb, int maxCost, int numSlns) {
      int maxQSize = 1;
      List<Solution> slns = new LinkedList<Solution>();
      Solution sln;
      AStarPath newStack = null, stack = new AStarPath(prb);
      FullHeapPQueue<AStarPath> queue = new FullHeapPQueue<AStarPath>(1000,
         new Comparator<AStarPath>() {
            public int compare(AStarPath s1, AStarPath s2) {
               int cost1 = s1.getCost();
               int cost2 = s2.getCost();
               
               return cost1 > cost2 ? -1 : cost1 == cost2 ? 0 : 1;
            }
         }
      );
      
      Problem.Step step;
      Problem.State topState, newState;
      AStarStep newStep;
      Logger log = Logger.getLogger("global");
            
      stack.push(new AStarStep(prb.getStartState(), null));
      queue.add(stack);
      
      while (!queue.isEmpty() && slns.size() < numSlns) {
         stack = queue.getFront();
         queue.remove();
         topState = stack.top().mState;
         log.log(Level.INFO, "Dequeueing stack ending with\n" + topState
            + "Cost: " + stack.getCost() + " of which " + prb.getDistance(topState)
            + " is an estimate.");
         
         // Note: Cannot avoid putting finished paths through pqueue again since
         // we want them in order of their total path cost.
         if (prb.getDistance(topState) == 0) {
            log.log(Level.INFO, "New solution");
            sln = new Solution();
            sln.mCost = stack.getCost();
            sln.mSteps = new Problem.Step[sln.mCost];
            for (int ndx = sln.mSteps.length-1; ndx >= 0; ndx--) {
               sln.mSteps[ndx] = stack.top().mStep;
               stack.pop();
            }
            slns.add(sln);
         }
         else
            for (Iterator<Problem.Step> itr = topState.iterator();
             itr.hasNext();) {
               step = itr.next();
               log.log(Level.INFO, "Trying step " + step);
               newState = topState.applyStep(step);
               newStep = new AStarStep(newState, step);
               if (!stack.isOn(newStep)) {
                  newStack = (AStarPath)stack.clone();
                  newStack.push(newStep);
                  if (newStack.getCost() <= maxCost) {
                     log.log(Level.INFO, "Good step, taking it.");
                     queue.add(newStack);
                  }
               }
            }   
      }
//      System.out.println("Max Queue size was " + maxQSize + " and we pulled " + pathTot);
      return slns.toArray(new Solution[0]);      
   }
}
