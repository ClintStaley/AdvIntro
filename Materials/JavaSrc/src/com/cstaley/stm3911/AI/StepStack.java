package com.cstaley.stm3911.AI;

import com.cstaley.stm3911.Stacks3.LinkStack;

// Extends LinkStack<Problem.Step> to add a getCost() method.
public class StepStack extends LinkStack<Problem.Step> {
   private int mCost;

   public StepStack() {
      super();
      mCost = 0;
   }

   // Copy constructor - shares nodes (safe because popping only moves head pointer)
   public StepStack(StepStack other) {
      super(other);
      mCost = other.mCost;
   }

   public int getCost() {return mCost;}
   
   @Override
   public void push(Problem.Step step) {
      mCost += step.getCost();
      super.push(step);
   }

   @Override
   public Problem.Step pop() {
      Problem.Step step = super.pop();
      mCost -= step.getCost();
      return step;
   }

   // Create and return a Solution from the Steps in this stack
   // Leaves the original stack unchanged (one bug)
   public Solver.Solution makeSolution() {
      StepStack copy = new StepStack(this);
      Solver.Solution sln = new Solver.Solution();
      
      sln.mCost = copy.mCost;
      sln.mSteps = new Problem.Step[copy.getSize()];
      for (int ndx = sln.mSteps.length; ndx >= 0; ndx--) {
         sln.mSteps[ndx] = copy.top();
         copy.pop();
      }
      return sln;
   }
}
