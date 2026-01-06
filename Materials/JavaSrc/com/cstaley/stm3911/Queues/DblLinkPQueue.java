package com.cstaley.stm3911.Queues;

import java.util.*;

public class DblLinkPQueue<Item> extends DblLinkQueue<Item> {
   protected Comparator<Item> mCmp;
   
   public DblLinkPQueue(Comparator<Item> cmp) {mCmp = cmp;}

   public void add(Item val) {
   }
}