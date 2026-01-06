package com.cstaley.stm3911.Sorting;

import java.util.*;

//import org.junit.Test;

public class QuickSort {
   static protected final int BREAK_SIZE = 5;
   
   static private class Range {
      public int lo;
      public int hi;
      
      public Range(int lo, int hi) {
         this.lo = lo;
         this.hi = hi;
      }
   }
   
   // @Test
   public void bigTest() {
      Random rnd = new Random(1);
      double[] vals = new double[10000000];
      long start;
      
      for (int ndx = 0; ndx < vals.length; ndx++)
         vals[ndx] = rnd.nextDouble();
      
      start = System.currentTimeMillis();
      quickSort(vals, 0, vals.length);
      System.out.printf("We took %d mS\n", System.currentTimeMillis() - start);
   }

   public static void main(String args[]) {
      int ndx;
      double vals[];
      Scanner input = new Scanner(System.in);

      System.out.print("Enter count and values: ");
      vals = new double[input.nextInt()];
      if (vals.length <= 100)
         for (ndx = 0; ndx < vals.length; ndx++)
            vals[ndx] = input.nextDouble();
      else
         for (ndx = 0; ndx < vals.length; ndx++)
            vals[ndx] = Math.random();

      long start = System.currentTimeMillis();
      insertSort(vals, 0, vals.length);
      System.out.printf("IS took %d mS\n", System.currentTimeMillis() - start);
      
      start = System.currentTimeMillis();
      quickSort(vals, 0, vals.length);
      System.out.printf("QS took %d mS\n", System.currentTimeMillis() - start);

      if (vals.length <= 100)
         for (double val: vals)
            System.out.printf("%f ", val);
         System.out.println();
   }
   
   public static void quickSort(double[] vals, int lo, int hi) {
      int mid;

      if (hi - lo < BREAK_SIZE)
         insertSort(vals, lo, hi);
      else {
         mid = partition(vals, lo, hi);
         quickSort(vals, lo, mid);
         quickSort(vals, mid+1, hi);
      }
   }
   
   public static void insertSort(double[] vals, int lo, int hi) {
      int ndx, insNdx;
      double toInsert;

      for (ndx = lo + 1; ndx < hi; ndx++) {
         toInsert = vals[ndx];
         insNdx = ndx;
         while (insNdx > lo && toInsert <= vals[insNdx-1]) {
            vals[insNdx] = vals[insNdx-1];
            insNdx--;
         }
         vals[insNdx] = toInsert;
      }
   }

   public static int partition(double[] vals, int lo, int hi) {
      int endOfLow, toPlace;
      double pivot, temp;
      
      double[] pivots = new double[3];

      // Don't do this on ranges of size < 3
      pivots[0] = vals[lo];
      pivots[1] = vals[(lo+hi)/2];
      pivots[2] = vals[hi-1];

      insertSort(pivots, 0, 3);

      vals[lo] = pivots[0];
      vals[(lo+hi)/2] = pivots[2];
      vals[hi-1] = pivots[1];

      endOfLow = lo;                // No vals on the bottom yet
      pivot = vals[hi-1];           // Use last value as pivot

      for (toPlace = lo; toPlace < hi-1; toPlace++)
         if (vals[toPlace] < pivot) {       // vals[toPlace] goes in bottom part
            temp = vals[toPlace];
            vals[toPlace] = vals[endOfLow];
            vals[endOfLow] = temp;
            endOfLow++;
         }                         
         // No else needed -- vals[toPlace] stays in top part by default

      vals[hi-1] = vals[endOfLow];  // Move pivot to middle
      vals[endOfLow] = pivot;
      
      return endOfLow;                    // Return pivot location
   }
}

/* Turns out that this actually runs slower than the recursive version!
public static void quickSort(double[] vals, int lo, int hi) {
   int mid;
   Stack<Range> stk = new Stack<Range>();
   Range rng = new Range(lo, hi);
   
   while (rng != null) {
      if (rng.hi - rng.lo < BREAK_SIZE) {
         insertSort(vals, rng.lo, rng.hi);
         rng = stk.isEmpty() ? null : stk.pop();
      }
      else {
         mid = partition(vals, rng.lo, rng.hi);
         if (mid - rng.lo > rng.hi - (mid+1)) {
            stk.push(new Range(mid+1, rng.hi));
            rng.hi = mid;
         }
         else {
            stk.push(new Range(rng.lo, mid));
            rng.lo = mid+1;
         }
      }
   }
}
*/