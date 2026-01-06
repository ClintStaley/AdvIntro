package com.cstaley.stm3911.Stacks3;

public class LinkStack<Type> implements Stack<Type> {

   protected static class Node {
      public Object data;
      public Node next;
   };

   protected Node head;
   
   public LinkStack() {}               // Defaults suffice
   
   public LinkStack(LinkStack<Type> stk) {
      head = stk.head;                 // Share nodes, at least to start with.
   }

   @Override
   public int getSize() {return 0;}    // Revise.  Return single member datum!
   
   @Override
   public void push(Type val) {
      Node temp = new Node();
      
      temp.data = val;
      temp.next = head;
      head = temp;
   }
      
   @Override
   public Type top() {
      if (head == null)
         throw new NullPointerException("Stack underflow");
      return (Type)head.data;
   }
   
   @Override
   public Type pop() {
      Type rtn;
      
      if (head == null)
         throw new NullPointerException("Stack underflow");
      
      rtn = (Type)head.data;
      head = head.next;
      return rtn;
   }
   
   @Override
   public boolean isEmpty() {return head == null;}

   @Override
   public boolean isOn(Type val) {
      Node temp;
      
      for (temp = head; temp != null && !temp.data.equals(val);
       temp = temp.next)
         ;
         
      return temp != null;
   }
   
   // Return value at offset ndx, or throw Stack Underflow
   public Type get(int ndx) {
      Node temp;
      
      for (temp = head; temp != null && ndx > 0; temp = temp.next, ndx--)
         ;
      
      if (temp == null)
         throw new NullPointerException("Stack underflow");
      
      return (Type)temp.data;
   }
   
   @Override
   public boolean equals(Object obj) {
      LinkStack<Type> rhs;
      Node temp1, temp2;
      
      if (!(obj instanceof LinkStack))
         return false;
      rhs = (LinkStack<Type>)obj;
         
      for (temp1 = head, temp2 = rhs.head;
         temp1 != null && temp2 != null;
         temp1 = temp1.next, temp2 = temp2.next) {
         if (temp1.data != temp2.data)
            break;
            
      return temp1 == null && temp2 == null;
   }
   
   @Override
   // Return a stack-representation of form [ val | val | val ... ] with TOS 
   // at the right end.
   public String toString() {
      return "Fix me";
   }
}