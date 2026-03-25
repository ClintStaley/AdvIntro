package com.cstaley.stm3911.ProgTests.Answer;

import java.util.Iterator;

public class ArrQueue<Item> {
   protected static final int mIncrSize = 10;
   protected Object[] mData = new Object[mIncrSize];
   protected int mHead;
   protected int mTail;
   protected boolean mEmpty = true;
   
   public Item getFront() {return (Item)mData[mHead];}
   
   public boolean isEmpty() {return mEmpty;}
   
   // Do not change anything above this line.

   public void add(Item val) {
      if (mHead == mTail && !mEmpty) {
         Object[] oldData = mData;
         mData = new Object[mData.length + mIncrSize];
         System.arraycopy(oldData, mHead, mData, 0, oldData.length - mHead);
         System.arraycopy(oldData, 0, mData, oldData.length - mHead, mTail);
         mHead = 0;
         mTail = oldData.length;
      }
      mData[mTail] = val;
      mTail = (mTail + 1) % mData.length;
      mEmpty = false;
   }

   public void remove() {
      mHead = (mHead + 1) % mData.length;
      mEmpty = mHead == mTail;
   }
   
   public int getSize() {
      if (mHead < mTail || mEmpty)
         return mTail - mHead;
      else
         return mData.length - (mHead - mTail);
   }
   
   // Undo the most recent add.  If the queue is empty, do nothing. Consider
   // all cases, including a queue with just one item.
   public void undoAdd() {
      if (!mEmpty) {
         mTail = (mTail - 1 + mData.length) % mData.length;
         mEmpty = mHead == mTail;
      }
   }
   
   public static void main(String[] args) {
      ArrQueue<Integer> q = new ArrQueue<Integer>();

      q.add(1);
      q.add(2);
      q.add(3);
      System.out.printf("Size %d front %d\n", q.getSize(), q.getFront());
      for (int i = 0; i < 9; i++) {
         q.add(q.getFront() + i);
         q.remove();
      }
      System.out.printf("Size %d front %d\n", q.getSize(), q.getFront());
      q.undoAdd();
      System.out.printf("Size %d front %d\n", q.getSize(), q.getFront());
      q.remove();
      System.out.printf("Size %d front %d IsEmpty:%b\n", q.getSize(),
         q.getFront(), q.isEmpty());
      q.undoAdd();
      System.out.printf("Size %d front %d IsEmpty:%b\n", q.getSize(),
         q.getFront(), q.isEmpty());

      for (int i = 0; i < 100; i++)
         q.add(i);
      System.out.printf("Size %d front %d IsEmpty:%b\n", q.getSize(),
         q.getFront(), q.isEmpty());
      
      for (int i = 0; i < 100; i++)
         q.undoAdd();
      System.out.printf("Size %d front %d IsEmpty:%b\n", q.getSize(),
            q.getFront(), q.isEmpty());
   }
}
   
