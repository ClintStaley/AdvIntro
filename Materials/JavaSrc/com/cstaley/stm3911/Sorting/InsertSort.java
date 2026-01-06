package com.cstaley.stm3911.Sorting;

import java.util.Scanner;

public class InsertSort {
   public static void main(String args[]) {
      int ndx;
      int vals[];
      Scanner input = new Scanner(System.in);

      System.out.print("Enter count and values: ");
      vals = new int[input.nextInt()];
      for (ndx = 0; ndx < vals.length; ndx++)
         vals[ndx] = input.nextInt();

      insertSort(vals);

      for (int val: vals)
         System.out.printf("%d ", val);
      System.out.println();
   }

   public static void insertSort(int[] vals) {
      int ndx, insNdx;
      int toInsert;

      for (ndx = 1; ndx < vals.length; ndx++) {
         toInsert = vals[ndx];
         insNdx = ndx;
         while (insNdx > 0 && toInsert <= vals[insNdx-1]) {
            vals[insNdx] = vals[insNdx-1];
            insNdx--;
         }
         vals[insNdx] = toInsert;

         for (int val: vals)
            System.out.printf("%d ", val);
         System.out.println();
      }
   }
}