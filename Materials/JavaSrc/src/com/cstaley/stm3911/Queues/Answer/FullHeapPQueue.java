package com.cstaley.stm3911.Queues.Answer;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class FullHeapPQueue<Item> implements Iterable<Item> {
   protected static final float cSizeIncr = 1.2f;
   protected Entry[] data;
   protected int tail;
   protected Comparator<Item> cmp;

   // An Entry is a node in the heap.  It contains the item and its index
   // in the heap.  This is sufficient info to move the item up or down in the
   // heap.  Adjusting the value of item, and calling promote or demote,
   // will move the item to its new position in the heap.
   public class Entry {
      public Item item;
      private int idx;              // Altered only by promote and demote

      Entry(Item item, int idx) {
         this.item = item;
         this.idx = idx;
      }

      public void demote() {
         int idx = this.idx;
         int child = 2 * idx + 1;     // Start with left child

         while (child < tail) {
            if (child + 1 < tail &&   // Choose right child if it is larger
               cmp.compare(data[child].item, data[child + 1].item) < 0)
               child++;
            if (cmp.compare(item, data[child].item) >= 0)
               break;
            data[child].idx = idx;
            data[idx] = data[child];
            idx = child;
            child = 2 * idx + 1;
         }
         data[idx] = this;
         this.idx = idx;
      }

      public void promote() {
         int idx = this.idx;
         int parent = (idx - 1) / 2;

         while (idx > 0 && cmp.compare(data[parent].item, item) < 0) {
            data[parent].idx = idx;   // Demote parent
            data[idx] = data[parent];
            idx = parent;             // Use parent's vacated spot as idx
            parent = (idx - 1) / 2;
         }
         this.idx = idx;
         data[idx] = this;
      }
   }

   // Make an iterator by copy construction the HPQueue
   // This iterator will not be able to modify the queue, but it can
   // iterate through the queue.
   protected class HPIterator<Item> implements Iterator<Item> {
      private FullHeapPQueue<Item> copy;

      HPIterator() {
         copy = new FullHeapPQueue<Item>(FullHeapPQueue.this);
      }
      @Override
      public boolean hasNext() {
         return !copy.isEmpty();
      }
      @Override
      public Item next() {
         Item rtn = copy.getFront();

         copy.remove();
         return rtn;
      }
   }

   public FullHeapPQueue(int initSize, Comparator<Item> cmp) {
      this.cmp = cmp;
      data = (Entry[]) java.lang.reflect.Array.newInstance(Entry.class,
            initSize);
      tail = 0;
   }

   public <T> FullHeapPQueue(FullHeapPQueue<T> q) {
      this.cmp = (Comparator<Item>) q.cmp;
      this.tail = q.tail;
      this.data = (Entry[]) java.lang.reflect.Array.newInstance(Entry.class,
            q.data.length);
      System.arraycopy(q.data, 0, data, 0, tail);
   }

   public Item getFront() {return data[0].item;}
   
   public int getSize() {return tail;}
   
   public boolean isEmpty() {return tail == 0;}
   
   // Repeatedly find parent (idx-1)/2 and move down to child at idx while
   // the parent is less than or equal to the child
   public Entry add(Item val) {
      if (tail == data.length) {
         Entry[] oldData = data;
         data = (Entry[]) java.lang.reflect.Array.newInstance(Entry.class,
            Math.round(data.length * cSizeIncr));
         System.arraycopy(oldData, 0, data, 0, oldData.length);
      }

      Entry car = new Entry(val, tail++);
      car.promote();

      return car;
   }
   
   // Save the item at mTail-1, and reduce mTail.  Then, starting at the root,
   // repeatedly find the child with the largest value and move it up to the
   // parent until the child is less than or equal to the the saved item.  
   // Finally, put the saved item in the child position.
   public void remove() {
      Entry val = data[--tail];

      data[tail] = null; // for garbage collection
      val.idx = 0;       // Consider root position first.
      val.demote();      // Demote correct position
   }
   
   // Only other ArrQueues can be equal to this queue
   public boolean equals(Object obj) {
      if (obj instanceof FullHeapPQueue) {
         FullHeapPQueue<Item> q = (FullHeapPQueue<Item>) obj;
         if (q.getSize() != getSize())
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
   public static void checkQueue(FullHeapPQueue<Integer> pq, int num, Random rnd) {
      long totalAdded = 0;
      int toAdd;
      FullHeapPQueue<Integer>.Entry res;
      
      // Make a copy of the queue and test equals (and iterator since equals
      // uses it)
      FullHeapPQueue<Integer> copy = new FullHeapPQueue<Integer>(pq);
      if (!pq.equals(copy))
         System.out.println("Copy not equal to original");
      
      long start = System.currentTimeMillis();
      for (int ndx = 0; ndx < num; ndx++) {
         toAdd = rnd.nextInt(1000000);
         res = pq.add(toAdd);
         if (ndx % 2 == 0) {
            res.item -= 100000;
            res.demote();
         }
         else {
            res.item += 100000;
            res.promote();
         }
         totalAdded += toAdd;
      }

      int prior = pq.getFront();
      while (!pq.isEmpty()) {
         // System.out.printf("Val: %d\n", prior);
         if (pq.getFront() > prior)
            System.out.println("Out of order: " + prior + " " + pq.getFront());
         totalAdded -= pq.getFront();
         prior = pq.getFront();
         pq.remove();
      }

      if (totalAdded != 0)
         System.out.println("Total missing items: " + totalAdded);
      else
         System.out.println("All items removed");

      System.out.printf("Elapsed time: %5.2f seconds\n",
            (System.currentTimeMillis() - start) / 1000.0);
   }

   // Prompt for and read queue size from a Scanner, then create an integer 
   // HeapPQueue of that size,
   // and fill it with random integers.  Then remove items and test that they
   // are in decreasing order.  Use System.timeMillis() to time the
   // performance of the queue.
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      Random rand = new Random();
      FullHeapPQueue<Integer> pq = new FullHeapPQueue<Integer>(1000, 
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
   
