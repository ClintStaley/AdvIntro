package com.cstaley.stm3911.ProgTests.Answer;

public class MergeSort {
   public static void insertSort(double[] vals, int lo, int hi) {
      int ndx, insNdx;
      double toInsert;

      for (ndx = lo + 1; ndx < hi; ndx++) {
         toInsert = vals[ndx];
         insNdx = ndx;
         while (insNdx > lo && toInsert <= vals[insNdx - 1]) {
            vals[insNdx] = vals[insNdx - 1];
            insNdx--;
         }
         vals[insNdx] = toInsert;
      }
   }

   // Merge the two ranges in src into trg. The ranges are src[lo..mid) and
   // src[mid..hi). The merged range is trg[lo..hi).
   public static void mergeRanges(double[] src, double[] trg, int lo, int mid,
      int hi) {
      int srcLo = lo, srcHi = mid, trgNdx = lo;

      while (srcLo < mid || srcHi < hi)
         if (srcHi == hi || srcLo < mid && src[srcLo] <= src[srcHi])
            trg[trgNdx++] = src[srcLo++];
         else
            trg[trgNdx++] = src[srcHi++];
   }
   
   public static double[] mergeSort(double[] vals) {
      final int BREAK_SIZE = 10;
      // Create a second array to hold the merged values
      double[] temp = new double[vals.length];

      // Sort BREAK_SIZE groups of elements with insertSort.  The final group may
      // be smaller than BREAK_SIZE.
      for (int ndx = 0; ndx < vals.length; ndx += BREAK_SIZE)
         insertSort(vals, ndx, Math.min(ndx + BREAK_SIZE, vals.length));
      
      // Merge the sorted ranges, doubling the size of the sorted ranges each 
      // time, until the entire array is sorted. Merge from vals into temp, then
      // swap the arrays and repeat.
      for (int rangeSize = BREAK_SIZE; rangeSize < vals.length; rangeSize *= 2) {
         for (int ndx = 0; ndx < vals.length; ndx += 2 * rangeSize)
            mergeRanges(vals, temp, ndx, Math.min(ndx + rangeSize, vals.length),
               Math.min(ndx + 2 * rangeSize, vals.length));

         double[] swap = vals;
         vals = temp;
         temp = swap;
      }
      return vals;
   }

   public static void main(String args[]) {
      double vals[];

      // Create 10,000,000 random values
      vals = new double[10000000];
      for (int ndx = 0; ndx < vals.length; ndx++)
         vals[ndx] = Math.random();

      // Sort them
      System.out.println("Sorting");
      vals = mergeSort(vals);
      System.out.println("Done");

      // Validate that they are sorted
      for (int idx = 0; idx < vals.length - 1; idx++)
         if (vals[idx] > vals[idx + 1]) {
            System.out.println("Sort failed");
            return;  
         }  
   }  
}
