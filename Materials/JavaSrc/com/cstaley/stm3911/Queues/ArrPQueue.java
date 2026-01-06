package com.cstaley.stm3911.Queues;

import java.util.*;

public class ArrPQueue<Item> extends ArrQueue<Item> {
   protected Comparator<Item> mCmp;
   
   public ArrPQueue(Comparator<Item> cmp) {mCmp = cmp;}
      
   public void add(Item val) {
      // Make absolutely no calls of arraycopy or new.
   }
}

// Sample (slightly buggy) Comparator
class IntegerComparator implements Comparator<Integer> {
   public int compare(Integer o1, Integer o2) {
      return o1 - o2;  // Why is this a bug?
   }
}
   
