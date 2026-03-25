package com.cstaley.stm3911.Queues;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkQueue<Item> implements Queue<Item>, Iterable<Item> {
   protected static class Node {
      public Object data;
      public Node next;

      public Node(Object d, Node n) {
         data = d;
         next = n;
      }
   }

   protected Node mHead;
   protected Node mTail;
   protected int modCount = 0; // Track modifications for fail-fast iterator

   public LinkQueue() {
      mHead = mTail = null;
   }

   // Static factory method demonstrating varargs and type inference
   @SafeVarargs
   public static <T> LinkQueue<T> of(T... items) {
      LinkQueue<T> q = new LinkQueue<>();
      for (T item : items)
         q.add(item);
      return q;
   }

   // Make this queue a deep copy of "other". Do this efficiently without
   // calling other methods. (Can this be done shallowly like LinkStack?)
   // Be sure it works correctly even if other is empty.
   public LinkQueue(LinkQueue<Item> other) {
      // Deep copy the other queue without calling other methods.
   }

   // Method cascading: return this to allow fluent API style
   @Override
   public LinkQueue<Item> add(Item val) {
      Node temp = new Node(val, null);

      if (mHead != null)
         mTail.next = temp;
      else {
         mHead = temp;
      }
      modCount++; // Track modification for fail-fast iterator
      return this;
   }

   @Override
   public Item getFront() {
      if (mHead == null)
         throw new NullPointerException();
      return (Item) mHead.data;
   }

   @Override
   public void remove() {
      if (isEmpty())
         throw new NullPointerException();
      mHead = mHead.next;
      if (mHead == null)
         mTail = null;
      modCount++; // Track modification for fail-fast iterator
   }

   public boolean isEmpty() {
      return mHead == null;
   }

   protected class LinkIterator<E> implements Iterator<E> {
      private Node loc; // The node the iterator is on presently
      private int expectedModCount; // Snapshot of modCount when created

      public LinkIterator() {
         loc = mHead;
         expectedModCount = modCount; // Capture modification count
      }

      public boolean hasNext() {
         return loc != null;
      }

      public E next() {
         if (modCount != expectedModCount)
            throw new ConcurrentModificationException();

         Object toReturn = loc.data;
         loc = loc.next;
         return (E) toReturn;
      }
   }

   @Override
   public Iterator<Item> iterator() {
      return new LinkIterator<Item>();
   }

   // Add all nodes of "other" to the end of this queue, without using any kind
   // of loop. (Do it by changing pointers only.) Leave "other" a valid, empty
   // queue.
   public void addToEnd(LinkQueue<Item> other) {
      // Todo
   }

   // Remove the tail element of the queue, undoing the most recent add.
   // Throw NullPointerException if the queue is empty. You will need a loop.
   public void undoAdd() {
      // Todo
   }

   public static void main(String[] args) {
      LinkQueue<Double> q1 = new LinkQueue<>();

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
      for (Double d : q1)
         System.out.println(d);

      // Method cascading example
      LinkQueue<String> q2 = new LinkQueue<>();
      q2.add("first").add("second").add("third");
      System.out.println("Cascaded adds:");
      for (String s : q2)
         System.out.println(s);

      // Static factory method with varargs
      LinkQueue<Integer> q3 = LinkQueue.of(1, 2, 3, 4, 5);
      System.out.println("Factory method:");
      for (Integer i : q3)
         System.out.println(i);

      // Fail-fast iterator demonstration
      LinkQueue<Character> q4 = new LinkQueue<>();
      q4.add('a').add('b').add('c');
      Iterator<Character> itr = q4.iterator();
      System.out.println("Fail-fast test:");

      System.out.println(itr.next()); // 'a'
      q4.add('d'); // Modify queue during iteration
      try {
         System.out.println(itr.next()); // Should throw exception
      } catch (ConcurrentModificationException e) {
         System.out.println(e.getMessage());
      }

      /*
       * Tests for undoAdd, addToEnd, and copy constructor
       * LinkQueue<Double> q2 = new LinkQueue<Double>(q1);
       * LinkQueue<Double> q3 = new LinkQueue<Double>();
       * 
       * q1.undoAdd();
       * q1.undoAdd();
       * q1.addToEnd(q2);
       * q1.addToEnd(q3);
       * q3.addToEnd(q3);
       * System.out.printf("%b %f\n", q3.isEmpty(), q1.getFront()); // true 10.0
       * 
       * q1.remove(); // Should now be 10, 20, 30
       * q3.addToEnd(q1); // And transferred to q3, leaving q1 empty
       * q2.add(42.0);
       * q2.undoAdd();
       * // true, true, 10.0
       * System.out.printf("%b %b %f\n", q1.isEmpty(), q2.isEmpty(), q3.getFront());
       */
   }
}