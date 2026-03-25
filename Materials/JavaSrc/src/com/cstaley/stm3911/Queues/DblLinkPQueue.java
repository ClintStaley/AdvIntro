package com.cstaley.stm3911.Queues;

import java.util.*;

public class DblLinkPQueue<Item> extends DblLinkQueue<Item> {
   protected Comparator<Object> mCmp;
   
   public DblLinkPQueue(Comparator<Item> cmp) {mCmp = (Comparator<Object>)cmp;}

   public DblLinkPQueue<Item> add(Item val) {
      return this;
   }
}