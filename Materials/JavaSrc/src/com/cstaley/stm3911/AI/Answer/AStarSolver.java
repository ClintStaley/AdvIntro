package com.cstaley.stm3911.AI.Answer-------------------------------------------------------------------------------------------------------------------------------------------------------------------------;

import java.util.Comparator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cstaley.stm3911.Queues.Answer.FullHeapPQueue;
import com.cstaley.stm3911.AI.Problem;
import com.cstaley.stm3911.AI.Solver;
import com.cstaley.stm3911.AI.StepStack;
import com.cstaley.stm3911.AI.Solver.Solution;

public class AStarSolver implements Solver {
   static private class AStarStep implements Problem.Step {
      public Problem.Step mStep;
      public Problem.State mState;

      public AStarStep(Problem.State state, Problem.Step step) {
         mStep = step;
         mState = state;
      }

      public int getCost() {
         return mStep == null ? 0 : mStep.getCost();
      }

      public boolean equals(Object rhs) {
         AStarStep sRhs = (AStarStep) rhs;

         return mState.equals(sRhs.mState);
      }
   }

   static private class AStarPath extends StepStack {
      private int mHeadCost; // Heuristic cost of state at the TOS
      private Problem mPrb;

      public AStarPath(Problem prb) {
         super();
         mPrb = prb;
         mHeadCost = prb.getStartState().getDistance();
      }

      // Copy constructor
      public AStarPath(AStarPath other) {
         super(other);
         mHeadCost = other.mHeadCost;
         mPrb = other.mPrb;
      }

      public int getHeadCost() {
         return mHeadCost;
      }

      @Override
      public int getCost() {
         // Total path cost: step costs (from StepStack) plus head heuristic cost
         return super.getCost() + mHeadCost;
      }

      @Override
      public void push(Problem.Step step) {
         AStarStep aStep = (AStarStep) step;
         mHeadCost = aStep.mState.getDistance();
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
      AStarPath newStack = null, stack = new AStarPath(prb);
      FullHeapPQueue<AStarPath> queue = new FullHeapPQueue<AStarPath>(1000,
            new Comparator<AStarPath>() {
               public int compare(AStarPath s1, AStarPath s2) {
                  int cost1 = s1.getCost();
                  int cost2 = s2.getCost();

                  return cost1 > cost2 ? -1 : cost1 == cost2 ? 0 : 1;
               }
            });

      Problem.Step step;
      Problem.State topState, newState;
      AStarStep newStep;
      Logger log = Logger.getLogger("global");

      stack.push(new AStarStep(prb.getStartState(), null));
      queue.add(stack);

      while (!queue.isEmpty() && slns.size() < numSlns) {
         stack = queue.getFront();
         queue.remove();
         topState = ((AStarStep) stack.top()).mState;
         log.log(Level.INFO, "Dequeueing stack ending with\n" + topState
               + "Cost: " + stack.getCost() + " of which " + stack.getHeadCost()
               + " is an estimate.");

         // Note: Cannot avoid putting finished paths through pqueue again since
         // we want them in order of their total path cost.
         if (topState.isGoal()) {
            log.log(Level.INFO, "New solution");
            slns.add(stack.makeSolution());
         } else
            for (Iterator<Problem.Step> itr = topState.iterator(); itr.hasNext();) {
               step = itr.next();
               log.log(Level.INFO, "Trying step " + step);
               newState = topState.applyStep(step);
               newStep = new AStarStep(newState, step);
               if (!stack.isOn(newStep)) {
                  newStack = new AStarPath(stack);
                  newStack.push(newStep);
                  if (newStack.getCost() <= maxCost) {
                     log.log(Level.INFO, "Good step, taking it.");
                     queue.add(newStack);
                  }
               }
            }
      }
      return slns.toArray(new Solution[0]);
   }
}
