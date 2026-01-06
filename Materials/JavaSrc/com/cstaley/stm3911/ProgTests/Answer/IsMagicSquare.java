package com.cstaley.stm3911.ProgTests.Answer;

import java.util.Scanner;

public class IsMagicSquare {
   public static boolean isMagic(int[][] square) {
      int diag1 = 0, diag2 = 0, dim = square.length;
      int[] rows = new int[dim], cols = new int[dim];
      
      for (int row = 0; row < dim; row++) {
         diag1 += square[row][row];
         diag2 += square[dim - row - 1][row];
         for (int col = 0; col < dim; col++) {
            rows[row] += square[row][col];
            cols[col] += square[row][col];
         }
      }
      
      if (diag1 != diag2)
         return false;
      
      for (int idx = 0; idx < dim; idx++)
         if (rows[idx] != diag1 || cols[idx] != diag1)
            return false;

      return true;
   }

   public static boolean isMagic2(int[][] square) {
      int base = 0, dim = square.length, check, check2;
      
      for (int row = 0; row < dim; row++)
         base += square[row][0];
      
      // Check row totals
      for (int row = 0; row < dim; row++) {
         check = 0;
         for (int col = 0; col < dim; col++)
            check += square[row][col];
         if (check != base)
            return false;
      }
   
      // Check col totals
      for (int col = 0; col < dim; col++) {
         check = 0;
         for (int row = 0; row < dim; row++)
            check += square[row][col];
         if (check != base)
            return false;
      }
      
      check = check2 = 0;
      for (int idx = 0; idx < dim; idx++) {
         check += square[idx][idx];
         check2 += square[dim - idx - 1][idx];
      }
      
      if (check2 != base || check != base)
         return false;
      
      return true;
   }
   
   public static void main(String[] args) {
      int dim;
      int [][] square;
      Scanner in = new Scanner(System.in);
      
      System.out.print("Enter dimension followed by square of values: ");
      dim = in.nextInt();
      square = new int[dim][dim];
      for (int row = 0; row < square.length; row++)
         for (int col = 0; col < square[row].length; col++)
            square[row][col] = in.nextInt();
      
      if (isMagic(square))
         System.out.println("Magic square");
      else
         System.out.println("Not magic square");
   }
}

/* Sample runs
Enter dimension followed by square of values: 3
2 7 6
9 5 1
4 3 8
Magic square

Enter dimension followed by square of values: 3
2 7 6
9 5 1
4 3 7
Not magic square

Enter dimension followed by square of values: 2
1 2
2 1
Not magic square

Enter dimension followed by square of values: 5
11 24 7 20 3
4 12 25 8 16
17 5 13 21 9
10 18 1 14 22
23 6 19 2 15
Magic square

Enter dimension followed by square of values: 5
11 24 7 20 3
4 12 25 8 16
17 5 13 21 9
10 18 1 14 22
23 6 19 2 14
Not magic square

*/


