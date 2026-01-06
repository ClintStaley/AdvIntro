package com.cstaley.stm3911.Queues;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class HeapPQueue<Item> implements Queue<Item>, Iterable<Item> {
   protected static final float cSizeIncr = 1.2f;
   protected Object[] data;
   protected int tail;
   protected Comparator<Item> cmp;

   // Dummy methods for now
   protected class HPIterator<Item> implements Iterator<Item> {
      HPIterator() {
      }
      @Override
      public boolean hasNext() {
         return true;
      }
      @Override
      public Item next() {
         return null;
      }
   }

   public HeapPQueue(int initSize, Comparator<Item> cmp) {
      this.cmp = cmp;
      data = new Object[initSize];
      tail = 0;
   }
   
   public Item getFront() {
      return (Item)data[0];
   }
   
   public boolean isEmpty() {
      return tail == 0;
   }
   
   public void add(Item val) {

   }
   
   // Save the item at mTail-1, and reduce mTail.  Then, starting at the root,
   // repeatedly find the child with the largest value and move it up to the
   // parent until the child is less than or equal to the the saved item.  
   // Finally, put the saved item in the child position.
   public void remove() {

   }
   
   @Override
   public Iterator<Item> iterator() {
      return new HPIterator<Item>();
   }

   // Fill pq with num randome integers.  Then remove them and ensure they 
   // are in decreasing order. Use System.timeMillis() to time the
   // performance of the queue.  Track the totals of added and removed integers
   // to be sure they match
   public static void checkQueue(Queue<Integer> pq, int num, Random rnd) {
      long totalAdded = 0;
      int toAdd;
      long start = System.currentTimeMillis();

      for (int ndx = 0; ndx < num; ndx++) {
         toAdd = rnd.nextInt(1000000);
         pq.add(toAdd);
         totalAdded += toAdd;
      }

      int top = pq.getFront();
      while (!pq.isEmpty()) {
         if (pq.getFront() > top)
            System.out.println("Out of order: " + top + " " + pq.getFront());
         totalAdded -= pq.getFront();
         pq.remove();
         if (!pq.isEmpty())
            top = pq.getFront();
      }

      if (totalAdded != 0)
         System.out.println("Total missing items: " + totalAdded);
      else
         System.out.println("All items removed");

      System.out.printf("Elapsed time: %5.2f seconds\n",
         (System.currentTimeMillis() - start) / 1000.0);
   }

   // Repeatedly prompt for and read queue size from a Scanner, then call 
   // checkQueue to test the queue.  Stop when input is done.
   public static void main(String[] args) {
      // 
   }
}
   