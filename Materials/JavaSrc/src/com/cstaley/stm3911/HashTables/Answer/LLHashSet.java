package com.cstaley.stm3911.HashTables.Answer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LLHashSet<E> implements Set<E> {
   private List<Object>[] table;
   private int valCount;
   
   private class LLItr implements Iterator<E> {
      private int idx = -1;
      private Iterator subItr;
      
      LLItr() {advance();}
      
      private void advance() {
         if (subItr == null || !subItr.hasNext()) {
            while (++idx < table.length && table[idx] == null)
               ;
            subItr = idx < table.length ? table[idx].iterator() : null;
         }
      }
      
      @Override
      public E next() {
         Object rtn = subItr.next();
         
         advance();
         return (E) rtn;
      }
      
      @Override
      public boolean hasNext() {
         return subItr != null;
      }
   }
   
   public LLHashSet(int size) {
      table = new LinkedList[size];
   }

   @Override
   public int size() {return valCount;}

   @Override
   public boolean isEmpty() {return valCount == 0;}

   @Override
   public boolean contains(Object toFind) {
      int loc = Math.abs(toFind.hashCode()) % table.length;
      
      return table[loc] == null ? false : table[loc].contains(toFind);
   }

   @Override
   public boolean add(E toAdd) {
      int loc = Math.abs(toAdd.hashCode()) % table.length;
      
      if (table[loc] == null)
         table[loc] = new LinkedList();
      if (table[loc].contains(toAdd))
         return false;
      table[loc].add(toAdd);
      valCount++;
      
      return true;
   }

   @Override
   public boolean remove(Object toDrop) {
      int loc = Math.abs(toDrop.hashCode()) % table.length;
      boolean rtn;
      
      rtn = table[loc] != null && table[loc].remove(toDrop);
      
      if (rtn)
         valCount--;
      
      if (table[loc].isEmpty())
         table[loc] = null;
      
      return rtn;
   }

   @Override
   public Iterator<E> iterator() {
      return new LLItr();
   }

   @Override
   public Object[] toArray() {
      Object[] rtn = new Object[valCount];
      int idx = 0;
      
      for (E obj: this)
         rtn[idx++] = obj;
      
      return rtn;
   }

   @Override
   public <T> T[] toArray(T[] a) {
      Object[] rtn = a.length >= valCount ? a : new Object[valCount];
      int idx = 0;
      
      for (Object obj: this)
         rtn[idx++] = obj;
      
      return (T[]) rtn;
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      for (Object toTest: c) {
         if (!contains(toTest))
            return false;
      }
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends E> toAdd) {
      boolean changed = false;
      
      for (Object val: toAdd)
         changed = changed | add((E)val);

      return changed;
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      boolean changed = false;
      List list;
      
      valCount = 0;
      for (int idx = 0; idx < table.length; idx++) {
         list = table[idx];
      
         if (list != null) {
            changed = changed | list.retainAll(c);
            if (list.isEmpty())
               table[idx] = null;
            else
               valCount += list.size();
         }
      }
         
      return changed;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      boolean changed = false;
      
      for (Object obj: c)
         changed = changed | remove(obj);
      
      return changed;
   }

   @Override
   public void clear() {
      table = new LinkedList[table.length];
      valCount = 0;
   }
   
   private boolean check() {
      int count = 0;
      List bucket;
      
      for (int idx = 0; idx < table.length; idx++) {
         bucket = table[idx];
         if (bucket != null) {
            if (bucket.isEmpty())
               return false;
            count += bucket.size();
            for (Object val: bucket)
               if (Math.abs(val.hashCode()) % table.length != idx)
                  return false;
         }
      }
      if (count != valCount)
         return false;
      
      return true;
   }
   
   @Override
   public String toString() {
      StringBuilder rtn = new StringBuilder();
      
      for (List lst: table) {
         if (lst == null)
            rtn.append("<null>\n");
         else {
            for (Object obj: lst)
               rtn.append(obj + " (" + obj.hashCode() + ") ");
            rtn.append("\n");
         }
      }
      return rtn.toString();
   }

   private static final int initSize = 8;
   
   public static void main(String[] args) {
      LLHashSet<String> set = new LLHashSet<String>(initSize);
      String cmd, arg;
      Scanner in = new Scanner(System.in);
      
      while (in.hasNext()) {
         cmd = in.next();
         if (cmd.equals("add"))
            System.out.print(set.add(in.next()));
         else if (cmd.equals("contains"))
            System.out.print(set.contains(in.next()));
         else if (cmd.equals("size"))
            System.out.print(set.size());
         else if (cmd.equals("isEmpty"))
            System.out.print(set.isEmpty());
         else if (cmd.equals("toString"))
            System.out.print(set);
         else if (cmd.equals("remove"))
            System.out.print(set.remove(in.next()));
         else if (cmd.equals("toString"))
            System.out.print(set);
         else if (cmd.equals("clear"))
            set.clear();
         else if (cmd.equals("iterate")) {
            for (String s: set)
               System.out.printf("%s ", s);
         }
         else if (cmd.equals("containsAll")) {
            System.out.print(set.containsAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("addAll")) {
            System.out.print(set.addAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("retainAll")) {
            System.out.print(set.retainAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("removeAll")) {
            System.out.print(set.removeAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         System.out.printf(" (%s)\n", set.check() ? "ok" : "NOT OK");
      }
   }
}
