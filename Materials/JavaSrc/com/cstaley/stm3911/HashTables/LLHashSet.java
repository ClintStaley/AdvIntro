package com.cstaley.stm3911.HashTables;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LLHashSet<E> implements Set<E> {
   private List<Object>[] table;
   private int valCount;

   // Iterator class for LLHashSet.  Iterates over the elements of each
   // LinkedList in the table, in order.
   private class LLItr<E> implements Iterator<E> {
      // Todo
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
      
      return table[loc] != null && table[loc].contains(toFind);
   }

   @Override
   public Iterator<E> iterator() {
      return LLItr<E>();   
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

   @Override
   public boolean add(E toAdd) {
      int loc = Math.abs(toAdd.hashCode()) % table.length;
      
      if (table[loc] == null)
         table[loc] = new LinkedList();
      
      if (!table[loc].contains(toAdd)) {
         table[loc].add(toAdd);
         valCount++;
         return true;
      }
      else
         return false;
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
   public boolean containsAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      return false;
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void clear() {
      table = new LinkedList[table.length];
      valCount = 0;
   }

   private boolean check() {
      int count = 0;
      List<Object> list;
      Object[] listArr;
      Object firstVal;
      
      for (int idx = 0; idx < table.length; idx++) {
         list = table[idx];
         if (list != null) {
            if (list.isEmpty())
               return false;
            count += list.size();

            listArr = list.toArray();
            for (int firstIdx = 0; firstIdx < listArr.length; firstIdx++) {
               firstVal = listArr[firstIdx];
               if (Math.abs(firstVal.hashCode()) % table.length != idx)
                  return false;
                  
               for (int secondIdx = firstIdx + 1; secondIdx < listArr.length; secondIdx++)
                  if (firstVal.equals(listArr[secondIdx]))
                     return false;
            }
         }
      }
      if (count != valCount)
         return false;
      
      return true;
   }
   
   private static final int initSize = 8;

   public static void main(String[] args) {
      LLHashSet<String> set = new LLHashSet<String>(initSize);
      String cmd;
      Scanner in = new Scanner(System.in);
      
      while (in.hasNext()) {
         cmd = in.next();
         if (cmd.equals("add"))
            System.out.print(set.add(in.next()));
         else if (cmd.equals("isEmpty"))
            System.out.print(set.isEmpty());
         // containsAll alpha   beta gamma delta        
         else if (cmd.equals("containsAll"))
            System.out.print(set.containsAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         System.out.printf(" (%s)\n", set.check() ? "ok" : "NOT OK");
      }
   }
}
