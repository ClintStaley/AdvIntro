package com.cstaley.stm3911.ProgTests.Answer;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

// Represent one Sudoku board, providing a method for reading the board from
// a Scanner, and a method for analyzing the board.  
public class Sudoku {

   // Represent one cell of the board, giving either a nonzero value or a
   // zero value and a set of possible values.
   private class Cell {
      private int value; // 0 for empty
      private Set<Integer> possibleValues;

      public Cell(int value) {
         this.value = value;
         if (value == 0)
            possibleValues = new HashSet<>();
      }
   }

   private Cell[][] board = new Cell[9][9];

   // Read the board from a Scanner, using 0 for empty cells.
   public void readBoard(Scanner in) {
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            int value = in.nextInt();
            board[i][j] = new Cell(value);
         }
      }
   }

   // Compute possible values for each empty cell in the Sudoku board, using NO
   // set methods for removing items.  Plan to add items only.
   public void computePossibleValues() {
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (board[i][j].value == 0) {
               Set<Integer> blockedValues = new HashSet<>();
               for (int k = 0; k < 9; k++) {
                  if (board[i][k].value != 0) {
                     blockedValues.add(board[i][k].value);
                  }
                  if (board[k][j].value != 0) {
                     blockedValues.add(board[k][j].value);
                  }
               }
               int boxRowStart = (i / 3) * 3;
               int boxColStart = (j / 3) * 3;
               for (int k = 0; k < 3; k++) {
                  for (int l = 0; l < 3; l++) {
                     if (board[boxRowStart + k][boxColStart + l].value != 0) {
                        blockedValues.add(board[boxRowStart + k][boxColStart + l].value);
                     }
                  }
               }
               
               board[i][j].possibleValues = new HashSet<>();
               for (int value = 1; value <= 9; value++) {
                  if (!blockedValues.contains(value)) {
                     board[i][j].possibleValues.add(value);
                  }
               }
            }
         }
      }
   }

   // Print the board, one line at a time, giving either the value of a cell 
   // or the possible values for an empty cell, in brackets.
   public void printBoard() {
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (board[i][j].value != 0) {
               System.out.print(board[i][j].value + " ");
            } else {
               // Print possible values for empty cells enclosed in brackets
               System.out.print("[ ");
               for (Integer value : board[i][j].possibleValues) {
                  System.out.print(value + " ");
               }
               System.out.print("] ");
            }
         }
         System.out.println();
      }
   }

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Sudoku sudoku = new Sudoku();
      sudoku.readBoard(in);
      sudoku.computePossibleValues();
      sudoku.printBoard();
   }
}

/*
 * Sample Input
 * 5 3 0 0 7 0 0 0 0
 * 6 0 0 1 9 5 0 0 0
 * 0 9 8 0 0 0 0 6 0
 * 8 0 0 0 6 0 0 0 3
 * 4 0 0 8 0 3 0 0 1
 * 7 0 0 0 2 0 0 0 6
 * 0 6 0 0 0 0 2 8 0
 * 0 0 0 4 1 9 0 0 5
 * 0 0 0 0 8 0 0 7 9
 * 
 * Corresponding Output
 * 5 3 [ 1 2 4 ] [ 2 6 ] 7 [ 2 4 6 8 ] [ 1 4 8 9 ] [ 1 2 4 9 ] [ 2 4 8 ]
 * 6 [ 2 4 7 ] [ 2 4 7 ] 1 9 5 [ 3 4 7 8 ] [ 2 3 4 ] [ 2 4 7 8 ]
 * [ 1 2 ] 9 8 [ 2 3 ] [ 3 4 ] [ 2 4 ] [ 1 3 4 5 7 ] 6 [ 2 4 7 ]
 * 8 [ 1 2 5 ] [ 1 2 5 9 ] [ 5 7 9 ] 6 [ 1 4 7 ] [ 4 5 7 9 ] [ 2 4 5 9 ] 3
 * 4 [ 2 5 ] [ 2 5 6 9 ] 8 [ 5 ] 3 [ 5 7 9 ] [ 2 5 9 ] 1
 * 7 [ 1 5 ] [ 1 3 5 9 ] [ 5 9 ] 2 [ 1 4 ] [ 4 5 8 9 ] [ 4 5 9 ] 6
 * [ 1 3 9 ] 6 [ 1 3 4 5 7 9 ] [ 3 5 7 ] [ 3 5 ] [ 7 ] 2 8 [ 4 ]
 * [ 2 3 ] [ 2 7 8 ] [ 2 3 7 ] 4 1 9 [ 3 6 ] [ 3 ] 5
 * [ 1 2 3 ] [ 1 2 4 5 ] [ 1 2 3 4 5 ] [ 2 3 5 6 ] 8 [ 2 6 ] [ 1 3 4 6 ] 7 9
 * 
 */

