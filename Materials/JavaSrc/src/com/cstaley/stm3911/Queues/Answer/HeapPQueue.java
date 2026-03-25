package com.cstaley.stm3911.Queues.Answer;

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
   public Item getFront() {return (Item)data[0];}
   
   public boolean isEmpty() {return tail == 0;}
   
   public void add(Item val) {
      if (tail == data.length) {
         Object[] oldData = data;
         data = new Object[Math.round(data.length * cSizeIncr)];
         System.arraycopy(oldData, 0, data, 0, oldData.length);
      }

      // Repeatedly find parent (idx-1)/2 and move down to child at idx
      // while the parent is less than or equal to the child
      int idx = tail;
      while (idx > 0) {
         int parent = (idx - 1) / 2;
         if (((Comparable<Item>)data[parent]).compareTo(val) > 0)
            break;
         data[idx] = data[parent];
         idx = parent;
      }
      data[idx] = val;
      tail++;
   }
   
   // Save the item at mTail-1, and reduce mTail.  Then, starting at the root,
   // repeatedly find the child with the largest value and move it up to the
   // parent until the child is less than or equal to the the saved item.  
   // Finally, put the saved item in the child position.
   public void remove() {
      Object val = data[--tail];
      int idx = 0;

      data[tail] = null; // for garbage collection
      while (idx < tail) {
         int maxChildIdx = 2 * idx + 1; // Start with left child
         if (maxChildIdx >= tail) // Quit if no children
            break;
         if (maxChildIdx + 1 < tail && // Use right child if exists and larger
               cmp.compare((Item) data[maxChildIdx], (Item) data[maxChildIdx + 1]) < 0)
            maxChildIdx++;

         // If largest child is not greater than val, quit
         if (cmp.compare((Item) data[maxChildIdx], (Item) val) <= 0)
            break;

         // Move largest child up to parent
         data[idx] = data[maxChildIdx];
         idx = maxChildIdx;
      }
      data[idx] = val;
   }
   
   public int getSize() {
      return tail;
   }
   
   // Only other ArrQueues can be equal to this queue
   public boolean equals(Object obj) {
      if (obj instanceof ArrQueue) {
         ArrQueue<Item> q = (ArrQueue<Item>)obj;
         if (getSize() != q.getSize())
            return false;
         
         Iterator<Item> itr = iterator();
         Iterator<Item> itr2 = q.iterator();
         while (itr.hasNext() && itr2.hasNext())
            if (!itr.next().equals(itr2.next()))
               return false;
         return !itr.hasNext() && !itr2.hasNext();
      }
      else
         return false;
   }
   
   @Override
   public Iterator<Item> iterator() {
      return new HPIterator<Item>();
   }

   // Fill pq with num randome integers. Then remove them and ensure they
   // are in decreasing order. Use System.timeMillis() to time the
   // performance of the queue. Track the totals of added and removed integers
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

   void promote()

   // Prompt for and read queue size from a Scanner, then create an integer 
   // HeapPQueue of that size,
   // and fill it with random integers.  Then remove items and test that they
   // are in decreasing order.  Use System.timeMillis() to time the
   // performance of the queue.

   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      Random rand = new Random();
      HeapPQueue<Integer> pq = new HeapPQueue<Integer>(1000, 
         new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
               return o1.compareTo(o2);
            }
         }
      );

      while (input.hasNextInt()) {
         int size = input.nextInt();
         checkQueue(pq, size, rand);
      }
   }
}
   
