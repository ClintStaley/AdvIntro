package com.cstaley.stm3911.Stacks2;

public class CountingArrStack extends ArrStack {
   public CountingArrStack() {
      super(); 
   }
   
   public CountingArrStack(CountingArrStack stk) {
      super(stk);
   }

   public final int getNumVals() {return count;}
}
