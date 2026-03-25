package com.cstaley.stm3911.Sorting.Answer;

import java.util.Scanner;

public class RevInsSort {

   public static void main(String args[]) {
      int ndx;
      int vals[];
      Scanner input = new Scanner(System.in);

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

      for (ndx = vals.length-2; ndx >= 0; ndx--) {
         toInsert = vals[ndx];
         insNdx = ndx;
         while (insNdx < vals.length-1 && toInsert > vals[insNdx+1]) {
            System.out.printf("%d <-> %d\n", toInsert, vals[insNdx+1]);
            vals[insNdx] = vals[insNdx+1];
            insNdx++;
         }
         vals[insNdx] = toInsert;
      }
   }
}

/* IHS Template
import java.util.Scanner;

public class RevInsSort {

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

      for (ndx = vals.length-2; ndx >= 0; ndx--) {
         toInsert = vals[ndx];
         insNdx = ndx;
         while ((<1:52:52:>)) {
            // Indicate when a swap occurs between two items
            System.out.printf("%d <-> %d\n", (<1:30:30:>));
            (<1:30:30>);
            (<1:15:15:>);
         }
         vals[insNdx] = toInsert;
      }
   }
}

Input 1:
6 6 5 4 3 2 1
Output 1:
2 <-> 1
3 <-> 1
3 <-> 2
4 <-> 1
4 <-> 2
4 <-> 3
5 <-> 1
5 <-> 2
5 <-> 3
5 <-> 4
6 <-> 1
6 <-> 2
6 <-> 3
6 <-> 4
6 <-> 5
1 2 3 4 5 6 

Input 2:
5 2 1 3 5 4
Output 2:
5 <-> 4
2 <-> 1
1 2 3 4 5 

Input 3 (hidden):
4 1 1 1 1
Output 3:
1 1 1 1



*/
