package com.cstaley.stm3911.ProgTests.Answer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.cstaley.stm3911.HashTables.LLHashSet;

public class LPHashSet<E> implements Set<E> {
   private Object[] table;
   private int valCount;
   private int steps;       // Total steps to find entries.
   private int nullCount;
   
   private class LPItr<E> implements Iterator<E> {
      private int idx;
      
      public LPItr() {advance();}
      
      private void advance() {
         for (idx++; idx < table.length
          && (table[idx] == null || table[idx] == wasFull); idx++)
            ;
      }
      
      @Override
      public E next() {
         Object obj = table[idx];
         
         advance();
         return (E) obj;
      }
      
      @Override
      public boolean hasNext() {return idx < table.length;}
   }

   static Object wasFull = new Object();
   
   public LPHashSet(int size) {
      table = new Object[size];
      nullCount = size;
   }

   private boolean check() {
      int vCount = 0, nCount = 0, sCount = 0, homeIdx;
      
      for (int idx = 0; idx < table.length; idx++) {
         if (table[idx] == null)
            nCount++;
         else if (table[idx] != wasFull) {
            vCount++;
            homeIdx = Math.abs(table[idx].hashCode()) % table.length;
            while (homeIdx != idx) {
               if (table[homeIdx] == null)
                  return false;
               homeIdx = (homeIdx + 1) % table.length;
               sCount++;
            }
         }
      }
      
      return vCount == valCount && nCount == nullCount && sCount == steps;
   }
   
   @Override
   public boolean add(E toAdd) {
      int tSteps = 0;
      
      int addLoc = -1, idx = Math.abs(toAdd.hashCode()) % table.length;
      
      while (table[idx] != null && !table[idx].equals(toAdd)) {
         if (table[idx] == wasFull && addLoc == -1)
            addLoc = idx;
         if (addLoc == -1)
            tSteps++;
         idx = (idx + 1) % table.length;
      }
      
      if (table[idx] != null)
         return false;
      else {
         if (addLoc >= 0)
            table[addLoc] = toAdd;
         else {
            table[idx]  = toAdd;
            nullCount--;
         }
         valCount++;
         steps += tSteps;
         
         return true;
      }
   }
   
   @Override
   public String toString() {
      StringBuilder bld = new StringBuilder();
      
      for (int idx = 0; idx < table.length; idx++) {
         if (table[idx] == null)
            bld.append("null\n");
         else if (table[idx] == wasFull)
            bld.append("wasFull\n");
         else
            bld.append(table[idx]).append(" (")
             .append(Math.abs(table[idx].hashCode()) % table.length)
             .append(")\n");
      }
      bld.append("Avg steps: ").append(valCount == 0 ? 0 : (float)steps/valCount);
      return bld.toString();      
   }
   
   @Override
   public boolean contains(Object toFind) {
      int idx = Math.abs(toFind.hashCode()) % table.length;
      
      while (table[idx] != null 
       && (table[idx] != wasFull || !table[idx].equals(toFind)))
         idx = (idx + 1) % table.length;

      return table[idx] != null;
   }

   @Override
   public int size() {return valCount;}

   @Override
   public boolean isEmpty() {return valCount == 0;}

   @Override
   public void clear() {
      table = new Object[table.length];
      valCount = 0;
   }
   
   @Override
   public Iterator<E> iterator() {
      return new LPItr(); 
   }

   @Override
   public boolean remove(Object toDrop) {
      int tSteps = 0;
      int idx = Math.abs(toDrop.hashCode()) % table.length;
      
      while (table[idx] != null 
       && (table[idx] == wasFull || !table[idx].equals(toDrop))) {
         idx = (idx + 1) % table.length;
         tSteps++;
      }
      
      if (table[idx] != null) {
         table[idx] = wasFull;
         valCount--;
         steps -= tSteps;
         return true;
      }
      else
         return false;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      boolean rtn = false;
      
      for (Object val: c)
         rtn = rtn | remove(val);
      
      return rtn;
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
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public Object[] toArray() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public <T> T[] toArray(T[] a) {
      // TODO Auto-generated method stub
      return null;
   }

   public static void main(String[] args) {
      final int initSize = 8;
      LPHashSet<String> set = new LPHashSet<String>(initSize);
      String cmd;
      Scanner in = new Scanner(System.in);

      while (in.hasNext()) {
         cmd = in.next();
         System.out.printf("\nDoing %s ------------\n", cmd);
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
            for (String s : set)
               System.out.printf("%s ", s);
         } else if (cmd.equals("containsAll")) {
            System.out.print(set.containsAll(Arrays.asList(in.nextLine().trim().split(" +"))));
         } else if (cmd.equals("addAll")) {
            System.out.print(set.addAll(Arrays.asList(in.nextLine().trim().split(" +"))));
         } else if (cmd.equals("removeAll")) {
            System.out.print(set.removeAll(Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         System.out.printf(" (%s)\n", set.check() ? "ok" : "NOT OK");
      }
   }
}
