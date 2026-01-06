package com.cstaley.stm3911.Stacks2;

public class ArrStack extends Object implements Stack {
   protected int[] data;
   protected int count;
   protected static final int sizeIncr = 10;
   
   public ArrStack() {
      data = new int[sizeIncr];  // or this(sizeIncr);
   }
   
   public ArrStack(int size) {
      data = new int[size];
   }
   
   // Inefficient.  Could be much faster.
   public ArrStack(ArrStack stk) {
      for (int ndx = 0; ndx < count; ndx++)
         push(stk.data[ndx]);
   }
   
   @Override
   // Return a string representation of the stack of form "[1, 2, 3]" with top
   // of stack at right. 
   public String toString() {
      return "Fix me";
   }
   
   @Override
   public void push(int val) {
      int[] temp;
      
      if (count == data.length) {
         temp = new int[count + sizeIncr];
         System.arraycopy(data, 0, temp, 0, count);
         data = temp;   
      }
      data[count++] = val;
   }
   
   @Override
   public int top() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      return data[count-1];
   }
   
   @Override
   public void pop() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      count--;
   }
   
   @Override
   public boolean equals(Object obj) {
      ArrStack stk;
      
      if (!(obj instanceof ArrStack))
         return false;
      stk = (ArrStack)obj;
         
      if (count != stk.count)
         return false;
         
      for (int ndx = 0; ndx < count; ndx++)
         if (data[ndx] != stk.data[ndx])
            return false;
            
      return true;
   }
   
   @Override
   public boolean isOn(int val) {
      int ndx;
      
      for (ndx = 0; ndx < count && data[ndx] != val; ndx++)
         ;
      return ndx < count;
   }
   
   @Override
   public boolean isEmpty() {return count == 0;}

   // Test main that points a ArrStack to a CountingArrStack
   public static void main(String[] args) {
      ArrStack stk = new CountingArrStack();
      
      stk.push(42);
      System.out.println(stk.top());
      System.out.println(stk.isEmpty());
      System.out.println(stk.isOn(42));
   }
}