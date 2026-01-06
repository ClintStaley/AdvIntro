// Stack 1
package com.cstaley.stm3911.Stacks1;

public class Stack {
   private int[] data;
   private int count;
   private static final int sizeIncr = 10;

   public Stack() {
      count = 0;
      data = new int[sizeIncr];
   }

   // Stack stk1 = new Stack(stk2);
   // Inefficient. Could be much faster.
   public Stack(Stack stk) {
      for (int ndx = 0; ndx < count; ndx++)
         push(stk.data[ndx]);
   }

   // stk1.push(42) =>  push(stk1, 42)
   // stk2.push(42) =>  push(stk2, 42)
   public void push(int val) {
      int[] temp;

      if (count == data.length) {
         temp = new int[count + sizeIncr];
         System.arraycopy(data, 0, temp, 0, count);
         this.data = temp;
      }
      this.data[count++] = val;
   }

   public int top() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      return data[count-1];
   }
   
   public void pop() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      count--;
   }

   // Not part of the traditional Stack interface, but illustrates ideas.  
   public void push(int... vals) {
      for (int idx = 0; idx < vals.length; idx++)
         push(vals[idx]);
      /* or
      for (int val: vals)
         push(val);
      */
   }

   // if (stk1.equals(stk2) => equals(stk1, stk2)
   public boolean equals(Stack stk) {
      if (count != stk.count)
         return false;

      for (int ndx = 0; ndx < count; ndx++)
         if (data[ndx] != stk.data[ndx])
            return false;

      return true;
   }

   public boolean isOn(int val) {
      int ndx;

      for (ndx = 0; ndx < count && data[ndx] != val; ndx++)
         ;

      return true; // Fix this
   }

   public boolean isEmpty() {return count == 0;}
}