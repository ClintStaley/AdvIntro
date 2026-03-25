package com.cstaley.stm3911.ProgTests.Answer;
import java.util.Random;

public class Jitterbug {
   static class Direction {
      int rowDelta;
      int colDelta;
      Direction(int r, int c) {
         rowDelta = r;
         colDelta = c;
      }
   }
   
   static Direction[] directions = {
      new Direction(-1, 0), // up
      new Direction(1, 0), // down
      new Direction(0, -1), // left
      new Direction(0, 1), // right
      new Direction(-1, -1), // up-left
      new Direction(-1, 1), // up-right
      new Direction(1, -1), // down-left
      new Direction(1, 1) // down-right
   };

   // Return true iff square has same totals for all rows, cols, and both diagonals.
   public static void jitter(int[][] square, int startRow, int startCol,
      Random rnd) {
      int locRow = startRow;
      int locCol = startCol;
      int numZeros = 0;

      // Total all the row lengthx
      for (int row = 0; row < square.length; row++) {
         numZeros += square[row].length;
      }

      square[locRow][locCol] = 1;
      numZeros--;

      // Repeatedly choose one of the 8 elements of "directions" at random.
      // If that direction is in bounds (within both the number of rows, and the
      // length of the row moved to), then move locRow, locCol in that direction.
      // and increment the value at that location by 1.  Continue until all
      // elements are at least 1.  Do *not* use an inner loop for this.  Track
      // the number of zero-elements instead.
      while (numZeros > 0) {
         int dir = rnd.nextInt(directions.length);
         int newRow = locRow + directions[dir].rowDelta;
         int newCol = locCol + directions[dir].colDelta;
         if (newRow >= 0 && newRow < square.length && newCol >= 0 
            && newCol < square[newRow].length) {
            if (square[newRow][newCol] == 0)
               numZeros--;
            square[newRow][newCol]++;
            locRow = newRow;
            locCol = newCol;
         }
      }
   }

   public static void printSquare(int[][] square) {
      for (int row = 0; row < square.length; row++) {
         for (int col = 0; col < square[row].length; col++)
            System.out.printf("%d ", square[row][col]);
         System.out.println();
      }
      System.out.println();
   } 

   public static void main(String[] args) {
      Random rnd = new Random(1);
      int [][] square;

      square = new int[10][10];
      jitter(square, 0, 0, rnd);
      printSquare(square);

      square = new int[3][10];
      jitter(square, 1, 5, rnd);
      printSquare(square);

      square = new int[5][];
      square[0] = new int[1];
      square[1] = new int[2];
      square[2] = new int[7];
      square[3] = new int[2];
      square[4] = new int[1];
      jitter(square, 2, 0, rnd);
      printSquare(square);
   }
}

/*
 * Expected output
 * 3 10 10 10 15 8 9 8 10 4
 * 6 14 18 20 18 16 13 12 8 7
 * 10 18 21 23 17 13 12 9 10 11
 * 5 21 15 11 23 20 15 18 27 15
 * 7 14 15 20 15 17 14 22 24 9
 * 7 13 21 22 15 8 16 23 18 7
 * 11 19 25 19 10 9 11 14 13 13
 * 15 23 14 7 10 8 11 10 11 6
 * 16 23 10 8 10 6 10 8 7 3
 * 11 15 8 4 3 2 1 4 3 1
 * 
 * 3 5 5 4 1 2 4 2 2 1
 * 6 6 4 3 3 6 6 7 5 1
 * 3 4 3 2 2 3 5 4 5 1
 * 
 * 7
 * 15 15
 * 24 20 9 6 7 3 1
 * 21 24
 * 13
 */


