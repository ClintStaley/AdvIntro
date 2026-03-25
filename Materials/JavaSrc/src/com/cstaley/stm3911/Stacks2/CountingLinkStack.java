package com.cstaley.stm3911.Stacks2;

public class CountingLinkStack extends LinkStack {
   private int numVals;
   
   public CountingLinkStack() {
      super(); // Could also pass parameters if warranted
      numVals = 0;
   }
   
   public CountingLinkStack(CountingLinkStack stk) {
      super(stk);
   }

   public final int getNumVals() {return numVals;}
   
   @Override
   public void push(int val) {
      numVals++;
      super.push(val);
   }
   
   @Override
   public void pop() {
      super.pop();
      numVals--;
   }
}
