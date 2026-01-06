package com.cstaley.stm3911.AI.Answer;

import java.util.Set;

import com.cstaley.stm3911.AI.Problem;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;

public class SudokuPuzzle implements Problem {
   public static final int SIZE = 9;
   public static final Collection<Integer> allValues 
      = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

   
   private static class SudokuStep implements Problem.Step {
      public int mRow;
      public int mCol;
      public int mValue;

      public SudokuStep(int r, int c, int v) {
         mRow = r;
         mCol = c;
         mValue = v;
      }
      public int getCost() {return 1;}

      public String toString() {
         return "(" + mRow + ", " + mCol + " -> " + mValue + ")";
      }
   }

   private static class SudokuState implements Problem.State {
      // Represent one cell of the board, giving either a nonzero value or a
      // zero value and a set of possible values.
      private class Cell {
         private int mRow;
         private int mCol;
         private int value; // 0 for empty
         private Set<Integer> possibleValues;

         public Cell(int mRow, int mCol) {
            this.mRow = mRow;
            this.mCol = mCol;
            possibleValues = new HashSet<Integer>(allValues);
         }

         public Cell(Cell cell) {
            mRow = cell.mRow;
            mCol = cell.mCol;
            value = cell.value;
            possibleValues = cell.possibleValues != null ?
               new HashSet<Integer>(cell.possibleValues) : null;
         }

         public boolean lastPossible(int value) {
            return possibleValues != null
                  && possibleValues.size() == 1 && possibleValues.contains(value);
         }

         public void setValue(int val) {
            int boxRowStart = (mRow / 3) * 3;
            int boxColStart = (mCol / 3) * 3;

            value = val;
            possibleValues = null;

            // Remove value from possibleValues of all cells
            // in the same row, column, and box
            for (int i = 0; i < SIZE; i++) {
               if (board[i][mCol].value == 0) {
                  board[i][mCol].possibleValues.remove(value);
               }
               if (board[mRow][i].value == 0) {
                  board[mRow][i].possibleValues.remove(value);
               }
               if (board[boxRowStart + i / 3][boxColStart + i % 3].value == 0) {
                  board[boxRowStart + i / 3][boxColStart + i % 3]
                     .possibleValues.remove(value);
               }
            }
            distance--;
         }
      }

      private class SudokuIterator implements Iterator<Problem.Step> {
         private int mRow; // Row and column of the current empty cell
         private int mCol;
         private int boxRowStart;
         private int boxColStart;
         private Iterator<Integer> mValues;
         private int mValue; // Next value to return
         
         // Traverse mValues  to find the next value that doesn't leave another 
         //empty cell with no possible values.
         private void advance() {
            int testVal, i;

            mValue = -1;
            while (mValue == -1 && mValues.hasNext()) {
               testVal = mValues.next();
               // Check if testVal would leave any empty cell in the same
               // row or column or box with at least one possible value.
               for (i = 0; i < SIZE; i++) {
                  if (i != mRow && board[i][mCol].lastPossible(testVal)
                     || i != mCol && board[mRow][i].lastPossible(testVal)
                     || (boxRowStart + i/3 != mRow || boxColStart + i%3 != mCol) &&
                     board[boxRowStart + i/3][boxColStart + i%3].lastPossible(testVal))
                     break;
               }
               if (i == SIZE) 
                  mValue = testVal;
            }
         }

         public SudokuIterator() {
            mValue = -1;

            while (mRow < SIZE && board[mRow][mCol].value != 0) {
               mCol++;
               if (mCol == SIZE) {
                  mCol = 0;
                  mRow++;
               }
            }
                  
            if (mRow < SIZE) {
               boxRowStart = (mRow / 3) * 3;
               boxColStart = (mCol / 3) * 3;
               mValues = board[mRow][mCol].possibleValues.iterator();
               advance();
            }  
         }

         public boolean hasNext() {
            return mValue != -1;
         }

         public Step next() {
            SudokuStep rtn = new SudokuStep(mRow, mCol, mValue);
            advance();
            return rtn;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      }

      public Iterator<Step> iterator() {return new SudokuIterator();}

      private Cell[][] board;
      private int distance;

      public SudokuState() {
         board = new Cell[SIZE][SIZE];
         for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               board[i][j] = new Cell(i, j);
            }
         }
         distance = SIZE * SIZE;
      }

      public SudokuState(SudokuState state) {
         // Deep copy of the board
         board = new Cell[SIZE][SIZE];
         for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               board[i][j] = new Cell(state.board[i][j]);
            }
         }
         distance = state.distance;
      }

      public boolean equals(Object rhs) {
         SudokuState sState = (SudokuState)rhs;

         // Check for equal values in all cells of the respective boards.
         for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               if (board[i][j].value != sState.board[i][j].value) {
                  return false;
               }
            }
         }
         return true;
      }

      public State applyStep(Step step) {
         SudokuStep sStep = (SudokuStep)step;
         SudokuState rtn = new SudokuState(this);

         rtn.board[sStep.mRow][sStep.mCol].setValue(sStep.mValue);
         return rtn;
      }
      public String toString() {
         StringBuilder rtn = new StringBuilder();

         // Add the values of the cells to the StringBuilder.
         for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               if (board[i][j].value != 0) {
                  rtn.append(board[i][j].value + " ");
               } else {
                  // Append possible values for empty cells enclosed in brackets
                  rtn.append("[ ");
                  for (Integer value : board[i][j].possibleValues) {
                     rtn.append(value + " ");
                  }
                  rtn.append("] ");
               }
            }
            rtn.append("\n");
         }
         rtn.append("Distance: " + distance);
         return rtn.toString();
      }
   }

   @Override
   public State getStartState() {
      return mStartState;
   }

   @Override
   public int getDistance(State st) {
      SudokuState sState = (SudokuState)st;
      return sState.distance;
   }

   SudokuState mStartState;

   @Override
   public void read(Scanner in) {
      mStartState = new SudokuState();
      int value;
      SudokuState.Cell[][] board = mStartState.board;

      // Read the board from the Scanner
      for (int i = 0; i < SIZE; i++) {
         for (int j = 0; j < SIZE; j++) {
            value = in.nextInt();
            if (value != 0) {
               board[i][j].setValue(value);
            }
         }
      }
   }

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      SudokuPuzzle sudoku = new SudokuPuzzle();
      sudoku.read(in);
      System.out.println(sudoku.mStartState);
      for (Problem.Step step : sudoku.mStartState) {
         System.out.println(step);
      }
      System.out.println("Distance: " + sudoku.getDistance(sudoku.mStartState));

      // Apply first step to the start state
      sudoku.mStartState = (SudokuState)sudoku.mStartState.applyStep(sudoku.mStartState.iterator().next());
      System.out.println(sudoku.mStartState);
      for (Problem.Step step : sudoku.mStartState) {
         System.out.println(step);
      }
      System.out.println("Distance: " + sudoku.getDistance(sudoku.mStartState));
   }
}

/*
Sample Input
5 3 0 0 7 0 0 0 0
6 0 0 1 9 5 0 0 0
0 9 8 0 0 0 0 6 0
8 0 0 0 6 0 0 0 3
4 0 0 8 0 3 0 0 1
7 0 0 0 2 0 0 0 6
0 6 0 0 0 0 2 8 0
0 0 0 4 1 9 0 0 5
0 0 0 0 8 0 0 7 9

Corresponding Output
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
 9 8 0 7 0 0 3 0 0
 0 0 0 0 8 0 0 0 6
 0 0 0 0 5 9 2 7 0   
 0 7 0 0 0 5 0 0 0
 4 0 9 0 0 0 8 0 1
 0 6 0 2 0 8 0 0 3
 0 0 0 0 0 0 0 0 4
 0 0 0 9 0 6 0 0 0
 1 0 6 0 2 4 5 0 7

 */
