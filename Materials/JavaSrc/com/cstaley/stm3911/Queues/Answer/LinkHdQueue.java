package com.cstaley.stm3911.Queues.Answer;

import java.util.Iterator;

import com.cstaley.stm3911.Queues.Queue;

public class LinkHdQueue<Item> implements Queue<Item>, Iterable<Item> {   
   protected class LinkIterator<E> implements Iterator<E> {
      private Node loc;   // The node the iterator is on presently
         
      public LinkIterator() {
         loc = mHead.next;
      }
      
      public boolean hasNext() {
         return loc != null;
      }
      
      public E next() {
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
   
   public LinkHdQueue() {
      mHead = mTail = new Node(null, null);   // 2
   }
   
   // Make this queue a deep copy of "other". Do this the easy if slightly slow
   // way by using "add" and an iterator.  It can be done in two lines
   public LinkHdQueue(LinkHdQueue other) {
      this();
      for (Object obj: other)
         add(obj);
   }
   
   @Override
   public void add(Object val) {
      mTail.next = new Node(val, null);
      mTail = mTail.next;
   }
   
   @Override
   public Item getFront() {
      if (isEmpty())
         throw new NullPointerException();
      return (Item)mHead.next.data;
   }
   
   @Override
   public void remove() {
      if (isEmpty())
         throw new NullPointerException();
      mHead.next = mHead.next.next;
      if (mHead.next == null)
         mTail = mHead;
   }
   
   public boolean isEmpty() {
      return mHead == mTail;
   }
   
   // Add all nodes of "other" to the end of this queue, without using any kind
   // of loop.  (Do it by changing pointers only.)  Leave "other" a valid, empty
   // queue.
   public void addToEnd(LinkHdQueue other) {
      if (!other.isEmpty()) {
         mTail.next = other.mHead.next;
         mTail = other.mTail;
         other.mTail = other.mHead;
      }
   }
   
   // Remove the tail element of the queue, in effect undoing the most recent add.
   // Throw NullPointerException if the queue is empty.  You will need a loop.
   public void undoAdd() {
      Node temp;
      
      if (isEmpty())
         throw new NullPointerException();
      
      for (temp = mHead; temp.next != mTail; temp = temp.next)
         ;
      mTail = temp;
      temp.next = null;
   }
   
   public Iterator<Item> iterator() {
      return new LinkIterator<Item>();
   }
   
   public static void main(String[] args) {
      LinkHdQueue<Double> q1 = new LinkHdQueue<Double>();
      
      q1.add(10.0);
      q1.add(20.0);
      q1.add(30.0);
      
      Iterator<Double> outerItr, innerItr;
      outerItr = q1.iterator();
      while (outerItr.hasNext()) {
         Double outerVal = outerItr.next();
         innerItr = q1.iterator();
         while (innerItr.hasNext())
            System.out.printf("%f %f\n", outerVal, innerItr.next());
      }
      
      for (Double d: q1)
         System.out.println(d);
      
      LinkHdQueue<Double> q2 = new LinkHdQueue<Double>(q1);
      LinkHdQueue<Double> q3 = new LinkHdQueue<Double>();
      
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
   }
}