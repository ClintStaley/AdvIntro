package com.cstaley.stm3911.Queues.Answer;

import java.util.Iterator;

public class ArrQueue<Item> implements Queue<Item>, Iterable<Item> {
   protected static final int mIncrSize = 10;
   protected Object[] mData = new Object[mIncrSize];
   protected int mHead;
   protected int mTail;
   protected boolean mEmpty = true;

   protected class ArrIterator<Item> implements Iterator<Item> {
      private int mIndex = mHead;
      private int mCount = 0;

      private int oneLess(int index) {
         return (index - 1 + mData.length) % mData.length;
      }
      
      public boolean hasNext() {
         return mCount < getSize();
      }
      
      public Item next() {
         Item val = (Item)mData[mIndex];
    
         mIndex = (mIndex + 1) % mData.length;
         mCount++;
         return val;
      }

      @Override
      // Iterate through the queue to move items up one, removing the item at
      // mIndex.  Then move mTail up one.
      public void remove() {
         for (int i = oneLess(mIndex); i != mTail; i = (i + 1) % mData.length)
            mData[i] = mData[(i + 1) % mData.length];
         mTail = oneLess(mTail);
         mEmpty = mHead == mTail;
      }
   }
   
   public Item getFront() {return (Item)mData[mHead];}
   
   public boolean isEmpty() {return mEmpty;}
   
   public void add(Item val) {
      if (mHead == mTail && !mEmpty) {
         Object[] oldData = mData;
         mData = new Object[mData.length + mIncrSize];
         System.arraycopy(oldData, mHead, mData, 0, oldData.length-mHead);
         System.arraycopy(oldData, 0, mData, oldData.length-mHead, mTail);
         mHead = 0;
         mTail = oldData.length;
      }
      mData[mTail] = val;
      mTail = (mTail + 1) % mData.length;
      mEmpty = false;
   }
   
   public void remove() {       // 3 lines of code max
      mHead = (mHead + 1) % mData.length;
      mEmpty = mHead == mTail;
   }
   
   public int getSize() {
      if (mHead < mTail || mEmpty)
         return mTail - mHead;
      else
         return mData.length - (mHead - mTail);
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
      return new ArrIterator<Item>();
   }

   public static void main(String[] args) {
      ArrQueue<Integer> q = new ArrQueue<Integer>();
      for (int i = 0; i < 10; i++)
         q.add(i);
      for (int i : q)
         System.out.println(i);
      
      System.out.println("Removing");
      q.remove();
      Iterator<Integer> itr = q.iterator();
      itr.next();
      itr.remove();
      for (int i : q)
         System.out.println(i);

      System.out.println("Adding"); 
      for (int i = 0; i < 5; i++)
         q.add(i);
      for (int i : q)
         System.out.println(i);
   }
}
   
