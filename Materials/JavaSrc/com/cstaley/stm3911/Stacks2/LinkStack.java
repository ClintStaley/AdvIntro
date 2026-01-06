package com.cstaley.stm3911.Stacks2;

public class LinkStack extends Object implements Stack {

   protected static class Node {
      public int data;
      public Node next;
   };

   protected Node head;
   
   // Actually optional
   public LinkStack() {head = null;}
   
   // Efficient; doesn't use push()
   public LinkStack(LinkStack stk) {
      Node temp, last;
      
      head = null;
      
      if (stk.head != null) {
         head = new Node();
         head.data = stk.head.data;
         temp = stk.head;
         last = head;
         
         while (temp != null) {
            last.next = new Node();
            last.next.data = temp.data;
            temp = temp.next;
            last = last.next;
         }
         last.next = null;
      }
   }

   /* Or, would this actually work, despite being shallow??
    * public LinkStack(LinkStack stk) {
    *    head = stk.head;
    * }
    */
   
   @Override
   public void push(int val) {
      Node temp = new Node();
      
      temp.data = val;
      temp.next = head;
      head = temp;
   }
      
   @Override
   public int top() {
      if (head == null)
         throw new NullPointerException("Stack underflow");
      return head.data;
   }
   
   @Override
   public void pop() {
      if (head == null)
         throw new NullPointerException("Stack underflow");
      head = head.next;
   }
   
   @Override
   public boolean isEmpty() {return head == null;}

   @Override
   public boolean isOn(int val) {
      Node temp;
      
      for (temp = head; temp != null && temp.data != val;  temp = temp.next)
         ;
         
      return temp != null;
   }
   
   // Return value at offset ndx, or throw Stack Underflow
   public int valAt(int ndx) {
      Node temp;
      
      for (temp = head; ndx > 0;  temp = temp.next, ndx--)
         ;
      
      if (temp == null)
         throw new NullPointerException("Stack underflow");
      
      return temp.data;
   }

   // Return a string representation of the stack of form "[1, 2, 3]" with top
   // of stack at right. 
   public String toString() {
      return "Fix me";
   }
   
   @Override
   public boolean equals(Object obj) {
      LinkStack rhs;
      Node temp1, temp2;
      
      if (!(obj instanceof LinkStack))
         return false;
      rhs = (LinkStack)obj;
         
      for (temp1 = head, temp2 = rhs.head;
       temp1 != null && temp2 != null;
       temp1 = temp1.next, temp2 = temp2.next)
          if (temp1.data != temp2.data)
             break;
            
      return temp1 == null && temp2 == null;
   }
}
