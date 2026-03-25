package com.cstaley.stm3911.ProgTests;
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
   
   // Directions to move in, in the order: up, down, left, right,
   // up-left, up-right, down-left, down-right.
   static Direction[] directions = {
      new Direction(-1, 0), // up
      new Direction(1, 0), // down
      new Direction(0, -1), // left
      new Direction(0, 1), // right
     // Four more...
   };

   // Fill square by moving in random directions until all elements are at
   // least 1.  Start at (startRow, startCol) and move in one of the 8
   // directions, calling rnd.nextInt to determine which one.  If the chosen
   // new location is in bounds, increment the value at
   // that location by 1.  Continue until all elements are at least 1.
   // Do *not* use an inner loop for this.  Track the number of zero-elements
   public static void jitter(int[][] square, int startRow, int startCol,
      Random rnd) {
      int locRow = startRow;
      int locCol = startCol;
      int numZeros = 0;

      // Set numZeros to the total number of elements in the (possibly ragged)
      // array square.

      while (numZeros > 0) {
         // Choose a direction, and move there if it is in bounds.  Use
         // no loops in here.
      }
   }

   // Print square out, with each row on a separate line, and an an additional
   // blank line at the end. 
   public static void printSquare(int[][] square) {

   } 

   // Make no changes to main.  It is used to test your code.
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
