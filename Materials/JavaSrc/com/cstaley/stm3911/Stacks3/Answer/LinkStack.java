package com.cstaley.stm3911.Stacks3.Answer;

public class LinkStack<Type> implements Stack<Type> {

   protected static class Node {
      public Object data;
      public Node next;
   };

   protected Node head;
   protected int size;
   
   public LinkStack() {}               // Defaults suffice
   
   public LinkStack(LinkStack stk) {
      head = stk.head;                 // Share nodes, at least to start with.
   }
 
   @Override
   public int getSize() {return size;}
   
   @Override
   public void push(Type val) {
      Node temp = new Node();
      
      temp.data = val;
      temp.next = head;
      head = temp;
      size++;
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
      size--;
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
   @Override
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
   
   @Override
   public String toString() {
      StringBuilder rtn = new StringBuilder("]");
      
      for (Node temp = head; temp != null; temp = temp.next) {
         rtn.insert(0, temp.data);
         if (temp.next != null)
            rtn.insert(0, " | ");
      }
      rtn.insert(0, '[');
      
      return rtn.toString();
   }
   
   public static void main(String[] args) {
      LinkStack<Double> dblStack = new LinkStack<Double>();
      LinkStack<LinkStack<Double>> stkStack = new LinkStack<LinkStack<Double>>();
      
      System.out.println(dblStack);
      dblStack.push(42.0);
      dblStack.push(55.1);
      System.out.println(dblStack);
      
      stkStack.push(dblStack);
      stkStack.push(new LinkStack<Double>());
      System.out.println(stkStack);
   }
}