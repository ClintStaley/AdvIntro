
package com.cstaley.stm3911.Queues;

import java.util.Iterator;

public class LinkQueue<Item> implements Queue<Item>, Iterable<Item> {   
   protected class LinkIterator<E> implements Iterator<E> {
      private Node loc;   // The node the iterator is on presently
         
      public LinkIterator() {
         System.out.println("Creating an iterator");
         loc = mHead;
      }
      
      public boolean hasNext() {
         return loc != null;
      }
      
      public E next() {
         System.out.println("Doing next");
         Object toReturn;
         
         toReturn = loc.data;
         loc = loc.next;
         
         return (E) toReturn;
      }
   }
   
   protected static class Node {
      public Object data;
      public Node next;
      
      public Node(Object d, Node n) {data = d; next = n;}
   }
   
   protected Node mHead;
   protected Node mTail;
   
   public LinkQueue() {
      mHead = mTail = null;
   }
   
   // Make this queue a deep copy of "other".  Do this the easy if slightly slow
   // way by using "add" and an iterator.  It can be done in two lines
   public LinkQueue(LinkQueue other) {
      // Todo
   }
   
   @Override
   public void add(Item val) {
      Node temp = new Node(val, null);
      
      if (mHead != null)
         mTail.next = temp;
      else {
         mHead = temp;
      }
      mTail = temp;
   }
   
   @Override
   public Item getFront() {
      if (mHead == null)
         throw new NullPointerException();
      return (Item)mHead.data;
   }
   
   @Override
   public void remove() {
      if (isEmpty())
         throw new NullPointerException();
      mHead = mHead.next;
      if (mHead == null)
         mTail = null;
   }
   
   public boolean isEmpty() {
      return mHead == null;
   }
   
   // Add all nodes of "other" to the end of this queue, without using any kind
   // of loop.  (Do it by changing pointers only.)  Leave "other" a valid, empty
   // queue.
   public void addToEnd(LinkQueue other) {
      // Todo
   }
   
   // Remove the tail element of the queue, in effect undoing the most recent add.
   // Throw NullPointerException if the queue is empty.  You will need a loop.
   public void undoAdd() {
      // Todo  
   }
   
   public Iterator<Item> iterator() {
      return new LinkIterator<Item>();
   }
   
   public static void main(String[] args) {
      LinkQueue<Double> q1 = new LinkQueue<Double>();
      
      q1.add(10.0);
      q1.add(20.0);
      q1.add(30.0);

      // Iterator tests
      Iterator<Double> outerItr, innerItr;
      outerItr = q1.iterator();
      while (outerItr.hasNext()) {
         Double outerVal = outerItr.next();
         innerItr = q1.iterator();
         while (innerItr.hasNext())
            System.out.printf("%f %f\n", outerVal, innerItr.next());
      }
 
      // foreach loop automatically using our Iterable LinkQueue
      for (Double d: q1)
         System.out.println(d);
      
      
      /* Tests for undoAdd, addToEnd, and copy constructor
      LinkQueue<Double> q2 = new LinkQueue<Double>(q1);
      LinkQueue<Double> q3 = new LinkQueue<Double>();
      
      q1.undoAdd();
      q1.undoAdd();
      q1.addToEnd(q2);
      q1.addToEnd(q3);
      q3.addToEnd(q3);
      System.out.printf("%b %f\n", q3.isEmpty(), q1.getFront()); // true 10.0
      
      q1.remove();     // Should now be 10, 20, 30
      q3.addToEnd(q1); // And transferred to q3, leaving q1 empty
      q2.add(42.0);   
      q2.undoAdd();
      // true, true, 10.0
      System.out.printf("%b %b %f\n", q1.isEmpty(), q2.isEmpty(), q3.getFront());

      */
   }
}