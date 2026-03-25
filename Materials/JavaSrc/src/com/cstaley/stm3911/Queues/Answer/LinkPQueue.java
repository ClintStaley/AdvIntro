package com.cstaley.stm3911.Queues.Answer;

import java.util.Arrays;
import java.util.Comparator;

public class LinkPQueue<Item> extends LinkQueue<Item> {
   protected Comparator<Item> cmp;
   
   public LinkPQueue(Comparator<Item> cmp) {
      super();
      this.cmp = cmp;
   }
   
   @Override
   public void add(Item val) {
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
   // Do this without calling any other methods of the class, and with a single
   // loop through the list of items.
   public void mergeArray(Item[] items) {
      // Presort items in descending order, greatest first
      Arrays.sort(items, cmp.reversed());  // Assume this works; no need to fix
      
      // Keep track of the node to search from, initially null
      Node last = null;

      for (int idx = 0; idx < items.length; idx++) {
         Item item = items[idx];

         // Deal with possibility that first item goes at the head
         if (idx == 0 && (mHead == null || cmp.compare(mHead.data, item) < 0)) {
            mHead = last = new Node(item, mHead);
         }
         // Otherwise insert item at correct place after last.  Do not use mHead.
         else {
            if (last == null)
               last = mHead;

            while (last.next != null && cmp.compare(last.next.data, item) > 0) {
               last = last.next;
            }
            last.next = new Node(item, last.next);
            last = last.next;
         }
      }

      // Update tail if necessary
      if (last.next == null)
         mTail = last;
   }

   // Merge q2's nodes into the receiver object, leaving q2 empty. Write a
   // loop to directly merge the nodes, don't use recursion, an iterator
   // or any other methods of the class.
   public void merge(LinkPQueue<Item> q2) {
      Node prior = null, nd1 = mHead, nd2 = q2.mHead, nextMerge;

      while (nd2 != null) { 
         if (nd1 == null) {  // Rest of q2 adds to end of main queue
            nextMerge = nd2;
            mTail = q2.mTail;
            nd2 = null;
         } else if (cmp.compare((Item)nd1.data, (Item)nd2.data) > 0) {
            nextMerge = nd1;
            nd1 = nd1.next;
         } else {
            nextMerge = nd2;
            nd2 = nd2.next;
            nextMerge.next = nd1;
         }
         if (prior == null)
            mHead = nextMerge;
         else
            prior.next = nextMerge;
         prior = nextMerge;
      }
      q2.mHead = q2.mTail = null;
   }
}
