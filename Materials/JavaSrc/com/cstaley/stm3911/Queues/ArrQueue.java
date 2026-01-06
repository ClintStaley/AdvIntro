package com.cstaley.stm3911.Queues;

import java.util.Iterator;

public class ArrQueue<Item> implements Queue<Item> {
   protected static final int mIncrSize = 10;
   protected Object[] mData = new Object[mIncrSize];
   protected int mHead;
   protected int mTail;
   protected boolean mEmpty = true;
   
   public Item getFront() {return (Item)mData[mHead];}
   
   public boolean isEmpty() {return mEmpty;}
   
   public void add(Item val) {
      // Part of Project 1
      // Use no loops when resizing and copying; just arraycopy.
   }
   
   public void remove() {       // 3 lines of code max
      // Part of Project 1
   }
   
   public int getSize() {
      // Consider both wrapped and non-wrapped, and consider whether empty is true
      return 0;   // Placeholder
   }
   
   // Only other ArrQueues can be equal to this queue
   public boolean equals(Object obj) {
      // Part of Project 1  (Test very carefully)
      return false; // Placeholder
   }
   
   @Override
   public Iterator<Item> iterator() {
      return null;  // Placeholder
   }
}
   
