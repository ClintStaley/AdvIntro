package com.cstaley.stm3911.Stacks3;

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
   
   public ArrStack(ArrStack<Type> stk){
      data = stk.data.clone(); 
      count = stk.count;
   }
   
   @Override
   public int getSize() {return count;} 
   
   @Override
   public String toString() {
      return "Fix me";
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
}