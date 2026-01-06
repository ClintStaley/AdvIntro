package com.cstaley.stm3911.Queues.Answer;

import java.util.Iterator;

import com.cstaley.stm3911.Queues.Queue;

public class DblLinkQueue<Item> implements Queue<Item>, Iterable<Item> {
   protected static class Node {
      public Object data;
      public Node next;
      public Node prev;

      public Node() {}
      
      public Node(Object d, Node p, Node n) {
         data = d;
         next = n;
         prev = p;
      }
   }

   protected Node mHead;   
   
   // Check that queue is in a "good" state -- all pointers linked properly
   protected boolean isGood() {
      Node nd = mHead;
      
      do {
         if (nd.next.prev != nd || nd.prev.next != nd)
            return false;
         nd = nd.next;
      } while (nd != mHead);
      
      return true;
   }
   
   public DblLinkQueue() {
      mHead = new Node();
      mHead.next = mHead;
      mHead.prev = mHead.next;
   }
   
   public DblLinkQueue(DblLinkQueue<Item> src) {
      this();
      for (Item val: src)
         add(val);
   }

   @Override
   public void add(Item data) {
      Node toAdd = new Node(data, mHead.prev, mHead);
      mHead.prev.next = toAdd;
      mHead.prev = toAdd;
   }    

   public void undoAdd() {
      if (mHead == mHead.prev)
         throw new NullPointerException();
      mHead.prev = mHead.prev.prev;
      mHead.prev.next = mHead;
   }
   
   public void addToEnd(DblLinkQueue<Item> src ) {
      mHead.prev.next = src.mHead.next;
      src.mHead.next.prev = mHead.prev;
      mHead.prev = src.mHead.prev;
      mHead.prev.next = mHead;
      
      src.mHead.next = src.mHead.prev = src.mHead;   
   }
   
   @Override
   public boolean isEmpty() {
      return mHead == mHead.next;
   }
   
   @Override
   public Item getFront() {
      if (mHead == mHead.prev)
         throw new NullPointerException();
      return (Item)mHead.next.data;
   }
      
   @Override
   public void remove() {
      if (mHead == mHead.next)
         throw new NullPointerException();
      mHead.next = mHead.next.next;
      mHead.next.prev = mHead;
   }
   
   @Override
   public Iterator<Item> iterator() {
      return new Iterator<Item>() {
         Node loc = mHead.next;
         
         public boolean hasNext() {return loc != mHead;}
         public Item next()       {loc = loc.next; return (Item)loc.prev.data;}
         public void remove() {
            loc.prev = loc.prev.prev;
            loc.prev.next = loc;
         }
      };
   }
   
   public void swapFirstTwo() {
      Node second = mHead.next.next;  // Point to second node in queue
      
      if (mHead.next == mHead || second == mHead)  // Must have two nodes at least
         throw new NullPointerException();
      
      second.next.prev = mHead.next;
      second.prev = mHead;
      mHead.next.prev = second;
      mHead.next.next = second.next;
      second.next = mHead.next;
      mHead.next = second;
   }

   /*
   public static void main(String[] args) {
      DblLinkQueue<Double> q1 = new DblLinkQueue<Double>();
      
      q1.add(10.0);
      q1.add(20.0);
      q1.add(30.0);
      System.out.printf("IsGood: %b\n", q1.isGood());
      
      // Add more tests of your own here, one method at a time.
   }
   */
   
   /* Test main to use once you're done */
   public static void main(String[] args) {
      DblLinkQueue<Double> q1 = new DblLinkQueue<Double>();
      
      q1.add(20.0);
      q1.add(10.0);
      q1.swapFirstTwo();
      q1.add(30.0);
      System.out.printf("IsGood: %b\n", q1.isGood());
      
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
      
      DblLinkQueue<Double> itrQ = new DblLinkQueue<Double>(q1);
      
      outerItr = itrQ.iterator();
      outerItr.next();   // Set to first node
      outerItr.remove(); // Burn it
      outerItr.next();
      outerItr.next();   // Advance to last
      outerItr.remove(); // Remove it
      System.out.printf("%b %b %f\n", outerItr.hasNext(), itrQ.isEmpty(),
       itrQ.getFront());  // false false 20.0
      
      DblLinkQueue<Double> q2 = new DblLinkQueue<Double>(q1);
      DblLinkQueue<Double> q3 = new DblLinkQueue<Double>();
      
      q1.undoAdd();
      q1.undoAdd();
      q1.addToEnd(q2);
      q1.addToEnd(q3);
      q3.addToEnd(q3);
      // true true true
      System.out.printf("%b %b %b\n", q1.isGood(), q2.isGood(), q3.isGood());
      
      // true true 10.0
      System.out.printf("%b %b %f\n", q2.isEmpty(), q3.isEmpty(), q1.getFront()); 
      
      q1.remove();     // Should now be 10, 20, 30
      q3.addToEnd(q1); // And transferred to q3, leaving q1 empty
      q2.add(42.0);   
      q2.undoAdd();
      // true, true, 10.0
      System.out.printf("%b %b %f\n", q1.isEmpty(), q2.isEmpty(), q3.getFront());
   }
}