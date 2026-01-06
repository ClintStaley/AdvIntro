package com.cstaley.stm3911.ProgTests;
import java.util.Scanner;

public class IsMagicSquareTest {
   // Return true iff square has same totals for all rows, cols, and both diagonals.
   public static boolean isMagic(int[][] square) {
 
      
      
      
      
      
      
      
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


