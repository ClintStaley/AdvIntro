package com.cstaley.stm3911.Sorting;

import java.util.*;

public class GenericSorting {
	static protected final int BREAK_SIZE = 5;

	public static void main(String args[]) {
		int ndx;
		Double vals[];
      Scanner input = new Scanner(System.in);

		System.out.print("Enter count and values: ");
		vals = new Double[input.nextInt()];
		for (ndx = 0; ndx < vals.length; ndx++)
			vals[ndx] = input.nextDouble();       // Boxing

      quickSort(vals, new Comparator<Double>() {
         public int compare(Double o1, Double o2) {
            return o1.compareTo(o2);
         }
      });

		for (ndx = 0; ndx < vals.length; ndx++)
			System.out.print(vals[ndx] + " ");
		System.out.println();
      input.close();
	}

	public static <T> void insertSort(T[] vals, Comparator<T> cmp,
	 int lo, int hi) {
		int ndx, insNdx;
		T toInsert;

		for (ndx = lo + 1; ndx < hi; ndx++) {
			toInsert = vals[ndx];
			insNdx = ndx;                 
			while (insNdx > lo && cmp.compare(toInsert, vals[insNdx-1]) < 0) {
				vals[insNdx] = vals[insNdx-1];
				insNdx--;
			}
			vals[insNdx] = toInsert;
		}
	}

   public static <T> int partition(T[] vals,  Comparator<T> cmp,
      int lo, int hi) {
 
      int endOfLow, toPlace;
      T pivot, temp;
      
      T[] pivots = (T[]) new Object[3];

      // Don't do this on ranges of size < 3
      pivots[0] = vals[lo];
      pivots[1] = vals[(lo+hi)/2];
      pivots[2] = vals[hi-1];

      insertSort(pivots, cmp, 0, 3);

      vals[lo] = pivots[0];
      vals[(lo+hi)/2] = pivots[2];
      vals[hi-1] = pivots[1];

      endOfLow = lo;                // No vals on the bottom yet
      pivot = vals[hi-1];           // Use last value as pivot

      for (toPlace = lo; toPlace < hi-1; toPlace++)
         if (cmp.compare(vals[toPlace], pivot) < 0) {
            temp = vals[toPlace];
            vals[toPlace] = vals[endOfLow];
            vals[endOfLow] = temp;
            endOfLow++;
         }                         
         // No else needed -- vals[toPlace] stays in top part by default

      vals[hi-1] = vals[endOfLow];  // Move pivot to middle
      vals[endOfLow] = pivot;
      
      return endOfLow;
   }
   
   public static <T> void quickSort(T[] vals, Comparator<T> cmp) {
      quickSort(vals, cmp, 0, vals.length);
   }

   public static <T> void quickSort(T[] vals, Comparator<T> cmp,
    int lo, int hi) {
      int mid;

      if (hi - lo < BREAK_SIZE)
         insertSort(vals, cmp, lo, hi);
      else {
         mid = partition(vals, cmp, lo, hi);
         quickSort(vals, cmp, lo, mid);
         quickSort(vals, cmp, mid + 1, hi);
      }
   }
}

/*
Example: 2 12 14 3 9 11 6 10 4 10
 */