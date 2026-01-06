package com.cstaley.stm3911.Stacks3.Answer;

public class ArrStack<Type> implements Stack<Type> {
   protected Object[] data;
   protected int count;
   protected static final int sizeIncr = 10;
   
   public ArrStack() {
      data = new Object[sizeIncr];  // or this(sizeIncr);
   }
   
   public ArrStack(int size) {
      data = new Object[size];
   }
   
   public ArrStack(ArrStack stk){
      data = new Object[stk.count];
      System.arraycopy(stk.data, 0, data, 0, stk.count);
      count = stk.count;
   }
   
   @Override
   public int getSize() {return count;}    // Revise.  Return single member datum!
   
   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      
      result.append("[ ");
      for (int idx = 0; idx < count - 1; idx++) {
         result.append(data[idx]);
         result.append(" | ");
      }
      if (count > 0)
         result.append(data[count-1]);
      result.append(" ]");
      
      return result.toString();
   }
   
   @Override
   public void push(Type val) {
      Object[] temp;
      
      if (count == data.length) {
         temp = new Object[count + sizeIncr];
         System.arraycopy(data, 0, temp, 0, count);
         data = temp;   
      }
      data[count++] = val;
   }
   
   @Override
   public Type top() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      return (Type)data[count-1];
   }
   
   @Override
   public Type pop() {
      if (count == 0)
         throw new IndexOutOfBoundsException("Stack underflow");
      return (Type)data[--count];
   }
   
   @Override
   public Type get(int idx) {
      return (Type)data[count - idx - 1];
   }
   
   @Override
   public boolean equals(Object obj) {
      ArrStack<Type> stk;
      
      if (!(obj instanceof ArrStack))
         return false;
      stk = (ArrStack<Type>)obj;
         
      if (count != stk.count)
         return false;
         
      for (int ndx = 0; ndx < count; ndx++)
         if (data[ndx] != stk.data[ndx])
            return false;
            
      return true;
   }
   
   @Override
   public boolean isOn(Type val) {
      int ndx;
      
      // Is this the right way to compare??
      for (ndx = 0; ndx < count && !data[ndx].equals(val); ndx++)
         ;
      return ndx < count;
   }
   
   @Override
   public boolean isEmpty() {return count == 0;}
   
   // Extra variadic version of push for ArrStack only.
   public void push(Type... vals) {
      for (Type val: vals)
         push(val);
   }
   
}