package com.cstaley.stm3911.HashTables;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class LPHashSet<E> implements Set<E> {
   private Object[] table;
   private int valCount;

   static Object wasFull = new Object();

   private void reHash(int size) {

   }
   
   @Override
   public boolean add(E toAdd) {
      int idx = Math.abs(toAdd.hashCode()) % table.length;
 
      // Check for full and rehash if so
      if (valCount == table.length - 1) {
         reHash(table.length * 2);
         idx = Math.abs(toAdd.hashCode()) % table.length;
      }

      while (table[idx] != null && table[idx] != wasFull) {
         if (table[idx].equals(toAdd))
            return false;
         idx = (idx + 1) % table.length;
      }
      for (int searchIdx = idx; table[searchIdx] != null; 
         searchIdx = (searchIdx + 1) % table.length) {
         if (table[searchIdx].equals(toAdd))
            return false;
      }
      
      table[idx] = toAdd;
      valCount++;
      return true;
   }
   
   @Override
   public boolean contains(Object o) {
      // TODO Auto-generated method stub
      return false;
   }
   
   @Override
   public boolean addAll(Collection<? extends E> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void clear() {
      table = new Object[table.length];
      valCount = 0;
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean isEmpty() {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public Iterator<E> iterator() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean remove(Object o) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public int size() {
      // TODO Auto-generated method stub
      return 0;
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
      String[] sampleStrings = {"alpha", "alph", "beta", "gamma", "alpha"};
      Double[] sampleDoubles = {1.1, 2.5, 42.0, .9, 20.3};
      
      for (String s: sampleStrings) 
         System.out.printf("%s has hashcode %d\n", s, s.hashCode());

      for (Double d: sampleDoubles) 
         System.out.printf("%f has hashcode %d\n", d, d.hashCode());
   }
}
