package com.cstaley.stm3911.Queues;

import java.util.Arrays;
import java.util.Comparator;

public class LinkPQueue<Item> extends LinkQueue<Item> {
   protected Comparator<Object> cmp;
   
   public LinkPQueue(Comparator<Item> cmp) {
      super();
      this.cmp = (Comparator<Object>) cmp;
   }
   
   @Override
   public void add(Object val) {
      Node prior;
      Node temp = new Node(val, null);

      if (mHead == null)
         mHead = mTail = temp;
      else if (cmp.compare(mHead.data, temp.data) < 0) {
         temp.next = mHead;
         mHead = temp;
      }
      else {
         for (prior = mHead;
            prior.next != null && cmp.compare(prior.next.data, temp.data) > 0;
            prior = prior.next)
            ;
         if (prior.next == null)
            prior.next = mTail = temp;
         else {
            temp.next = prior.next;
            prior.next= temp;
         }
      }
   }

   // Merge the items in the array into the queue, maintaining the priority order
   // Do this without calling any other methods of the class, and without 
   // restarting from head for each item.
   public void mergeArray(Object[] items) {
      // Presort items in descending order, greatest first
      Arrays.sort(items, cmp.reversed());  // Assume this works; no need to fix
      
      // Keep track of the node to search from for next insert, initially null
      // in case next insert is at head.
      Node last = null;

      for (int idx = 0; idx < items.length; idx++) {
         Object item = items[idx];

         // Deal with possibility that first item goes at the head.  Note that
         // only the first item can go at the head; later items must go after
         // the last item inserted.
         if (                                                     ) {
            


         }
         // Otherwise insert item at correct place after last.  Do not use mHead.
         else {






         }
      }

      // Update tail if necessary
     
   }

   public static void main(String[] args) {
      LinkPQueue<Integer> pq = new LinkPQueue<Integer>(Integer::compareTo);
      Integer[] initItems = {3, 5, 7, 9, 2, 4, 6, 8};
      Integer[] moreItems = {1, 10, 0};
      pq.mergeArray(initItems);
      pq.mergeArray(moreItems);
      pq.add(11);
      while (!pq.isEmpty()) {
         System.out.printf("%d ", pq.getFront());
         pq.remove();
      }
      System.out.println();
   }
}
