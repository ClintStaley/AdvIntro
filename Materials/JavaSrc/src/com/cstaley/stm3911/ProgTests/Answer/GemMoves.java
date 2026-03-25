package com.cstaley.stm3911.ProgTests.Answer;

import java.util.Scanner;

public class GemMoves {
   public static void printArray(int[][] toPrint) {
      for (int row = 0; row < toPrint.length; row++) {
         for (int col = 0; col < toPrint[row].length; col++)
            System.out.print(toPrint[row][col] + " ");
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      int[][] square;
      int dim, zRow = 0, zCol = 0;
      Scanner in = new Scanner(System.in);
      
      System.out.print("Enter dimension followed by square of values: ");
      dim = in.nextInt();
      square = new int[dim][dim];
      for (int row = 0; row < square.length; row++)
         for (int col = 0; col < square[row].length; col++)
            square[row][col] = in.nextInt();

      for (int row = 0; row < square.length; row++)
         for (int col = 0; col < square[row].length; col++)
            if (square[row][col] == 0) {
               zRow = row;
               zCol = col;
            }
      
      if (zRow > 0)
         System.out.printf("Move %d down\n", square[zRow-1][zCol]);
      if (zRow < square.length-1)
         System.out.printf("Move %d up\n", square[zRow+1][zCol]);
      if (zCol > 0)
         System.out.printf("Move %d right\n", square[zRow][zCol-1]);
      if (zCol < square[0].length-1)
         System.out.printf("Move %d left\n", square[zRow][zCol+1]);
   }
}

/*
4
3 5 1 10
0 4 11 7
8 13 9 15
2 6 12 14
Move 3 down
Move 8 up
Move 4 right
*/

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


