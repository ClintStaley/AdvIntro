package com.cstaley.stm3911.Queues.Answer;

import java.util.Iterator;

public interface Queue<Item> {
   public abstract void add(Item val);
   public abstract Item getFront();
   public abstract void remove();
   public abstract boolean isEmpty();
   public abstract Iterator<Item> iterator();        // Return an iterator
}