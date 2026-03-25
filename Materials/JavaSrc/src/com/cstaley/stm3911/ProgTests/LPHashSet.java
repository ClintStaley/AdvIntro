package com.cstaley.stm3911.ProgTests;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.cstaley.stm3911.HashTables.LLHashSet;

public class LPHashSet<E> {
   private Object[] table;
   private int valCount;
   private int nullCount;
   
   static Object wasFull = new Object();
   
   public LPHashSet(int size) {
      table = new Object[size];
      nullCount = size;
   }

   private boolean check() {
      int vCount = 0, nCount = 0, homeIdx;
      
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
            }
         }
      }
      
      return vCount == valCount && nCount == nullCount;
   }
   
   public boolean add(E toAdd) {
      int addLoc = -1, idx = Math.abs(toAdd.hashCode()) % table.length;
      
      while (table[idx] != null && !table[idx].equals(toAdd)) {
         if (table[idx] == wasFull && addLoc == -1)
            addLoc = idx;
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
         
         return true;
      }
   }
   
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
      return bld.toString();      
   }

   public void clear() {
      table = new Object[table.length];
      valCount = 0;
   }
   
   public boolean remove(Object toDrop) {
      int idx = Math.abs(toDrop.hashCode()) % table.length;
      
      while (table[idx] != null 
       && (table[idx] == wasFull || !table[idx].equals(toDrop))) {
         idx = (idx + 1) % table.length;
      }
      
      if (table[idx] != null) {
         table[idx] = wasFull;
         valCount--;
         return true;
      }
      else
         return false;
   }
   
   public boolean removeAll(Collection<?> c) {
      boolean rtn = false;
      
      for (Object val: c)
         rtn = rtn | remove(val);
      
      return rtn;
   }

   public boolean addAll(Collection<? extends E> toAdd) {
      boolean changed = false;
      
      for (Object val: toAdd)
         changed = changed | add((E)val);

      return changed;
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
         else if (cmd.equals("toString"))
            System.out.print(set);
         else if (cmd.equals("remove"))
            System.out.print(set.remove(in.next()));
         else if (cmd.equals("clear"))
            set.clear();
         else if (cmd.equals("addAll")) 
            System.out.print(set.addAll(Arrays.asList(in.nextLine().trim().split(" +"))));
         else if (cmd.equals("removeAll")) 
            System.out.print(set.removeAll(Arrays.asList(in.nextLine().trim().split(" +"))));
         
         System.out.printf(" (%s)\n", set.check() ? "ok" : "NOT OK");
      }
   }
}

/* Basic tests.  Add more of your own
toString
addAll alpha beta gamma delta epsilon
toString
add theta
toString
remove delta
toString
add delta
toString
remove beta
remove theta
toString
*/

/* Output for the tests
 * Doing toString ------------
null
null
null
null
null
null
null
null
Avg steps: 0.0 (ok)

Doing addAll ------------
true (ok)

Doing toString ------------
beta (0)
delta (0)
epsilon (6)
null
null
null
alpha (6)
gamma (7)
Avg steps: 1.0 (ok)

Doing add ------------
true (ok)

Doing toString ------------
beta (0)
delta (0)
epsilon (6)
theta (6)
null
null
alpha (6)
gamma (7)
Avg steps: 1.6666666 (ok)

Doing remove ------------
true (ok)

Doing toString ------------
beta (0)
wasFull
epsilon (6)
theta (6)
null
null
alpha (6)
gamma (7)
Avg steps: 1.8 (ok)

Doing add ------------
true (ok)

Doing toString ------------
beta (0)
delta (0)
epsilon (6)
theta (6)
null
null
alpha (6)
gamma (7)
Avg steps: 1.6666666 (ok)

Doing remove ------------
true (ok)

Doing remove ------------
true (ok)


Doing toString ------------
wasFull
delta (0)
epsilon (6)
wasFull
null
null
alpha (6)
gamma (7)
Avg steps: 1.25 (ok)
*/

