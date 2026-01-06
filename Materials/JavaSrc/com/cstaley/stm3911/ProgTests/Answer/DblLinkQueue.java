package com.cstaley.stm3911.ProgTests.Answer;

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
   public boolean isEmpty() {
      return mHead == mHead.next;
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
   
//------------------
   
   public void undoRemove(Item putBack) {
      Node toAdd = new Node(putBack, mHead, mHead.next);
      
      mHead.next.prev = toAdd;
      mHead.next = toAdd;
   }
   
   // No if-statements or loops!
   public void addToStart(DblLinkQueue<Item> src ) {
      
      mHead.next.prev = src.mHead.prev;
      src.mHead.prev.next = mHead.next;
      mHead.next = src.mHead.next;
      mHead.next.prev = mHead;
      
      src.mHead.next = src.mHead.prev = src.mHead;   
   }
   
   @Override
   public String toString() {
      StringBuilder bld = new StringBuilder("[");
      
      for (Node temp = mHead.next; temp != mHead; temp = temp.next)
         bld.append(temp.data).append(temp.next == mHead ? "" : ", ");
      
      bld.append("]");
      return bld.toString();
   }
   
   /* Test main to use once you're done */
   public static void main(String[] args) {
      DblLinkQueue<Double> q1 = new DblLinkQueue<Double>();
      
      q1.add(20.0);
      q1.add(10.0);
      q1.add(30.0);
      q1.remove();
      q1.undoRemove(42.0);
      // After undoRemove: true [42.0, 10.0, 30.0]
      System.out.printf("After undoRemove: %b %s\n", q1.isGood(), q1);
      
      DblLinkQueue<Double> q2 = new DblLinkQueue<Double>();
      q2.add(50.0);
      q2.add(45.0);
      
      DblLinkQueue<Double> q3 = new DblLinkQueue<Double>();
      
      q1.addToStart(q2);
      q1.addToStart(q3);
      q3.addToStart(q3);
      
      // true [50.0, 45.0, 42.0, 10.0, 30.0]
      System.out.printf("%b %s\n", q1.isGood(), q1);
      // true []
      System.out.printf("%b %s\n", q2.isGood(), q2);
      // true []
      System.out.printf("%b %s\n", q3.isGood(), q3);
   }
}