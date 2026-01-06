package com.cstaley.stm3911.AI.Answer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.cstaley.stm3911.AI.Problem;

public class Maze implements Problem {
   public static final int MAX_DIM = 10;
   
   private static enum MazeStep implements Problem.Step {
      north(-1, 0),
      east(0, 1),
      south(1,0), 
      west(0, -1);
      
      public int getCost() {return 1;}
      
      public int mRowDiff;
      public int mColDiff;
      private MazeStep(int r, int c) {mRowDiff = r; mColDiff = c;}
   }
   
   static MazeStep[] mSteps = MazeStep.values();
      
   private class MazeState implements Problem.State {
      private class MazeIterator implements Iterator<Problem.Step> {
         int mNext;

         public MazeIterator() {
            mNext = -1;
            advance();
         }
         
         // Advance mNext to next valid GemStep, or to mSteps.length
         private void advance() {
            MazeStep step;
            int newRow, newCol;
            
            while (true) {
               if (++mNext == mSteps.length)
                  break;
               step = mSteps[mNext];
               
               newRow = mRow + step.mRowDiff;
               newCol = mCol + step.mColDiff;

               if (newRow >= 0 && newRow < mMaze.length && newCol >= 0
                && newCol < mMaze[0].length && mMaze[newRow][newCol])
                  break;
            }
         }
         
         public Step next(){
            Step rtn = mSteps[mNext];
            advance();
            
            return rtn;
         }
         public boolean hasNext()  {return mNext < mSteps.length;}
         public void remove()      {throw new UnsupportedOperationException();}
      }
      
      private int mRow;      // Current row
      private int mCol;      // Current col

      public MazeState(int r, int c) {
         mRow = r;
         mCol = c;
      }

      public Iterator<Step> iterator() {return new MazeIterator();}
      
      @Override
      public State applyStep(Step step) {
         MazeStep gStep = (MazeStep)step;

         return new MazeState(mRow + gStep.mRowDiff, mCol + gStep.mColDiff);
      }
      
      @Override
      public String toString() {
         return "(" + mRow + ", " + mCol + ")";
      }
      
      @Override
      public boolean equals(Object rhs) {
         MazeState msRhs = (MazeState) rhs;
         
         return mCol == msRhs.mCol && mRow == msRhs.mRow; 
      }
   }

   private boolean[][] mMaze;
   private MazeState mStartState; // Start location of maze
   private MazeState mEndState;   // Exit location of maze

   @Override
   public State getStartState() {return mStartState;}

   @Override
   public int getDistance(State st) {
      MazeState mState = (MazeState)st;
      
      return Math.abs(mState.mRow - mEndState.mRow)
       + Math.abs(mState.mCol - mEndState.mCol);
   }

   public void read(Scanner in) throws Exception {
      int rowDim, colDim, row, col;
      
      rowDim = in.nextInt();
      colDim = in.nextInt();
      if (rowDim > MAX_DIM || rowDim < 1
       || colDim > MAX_DIM || colDim < 1)
         throw new Exception("Dimensions must be between 1 and " + MAX_DIM);
      
      mMaze = new boolean[rowDim][colDim];
      for (row = 0; row < rowDim; row++)
         for (col = 0; col < colDim; col++)
            mMaze[row][col] = in.next().equals("X");
      
      row = in.nextInt();
      col = in.nextInt();
      System.out.printf("%d %d\n", row, col);
      if (col >= colDim || row >= rowDim || col < 0 || row < 0 || !mMaze[row][col])
         throw new Exception("Start location must be within maze");
      mStartState = new MazeState(row, col);
      
      row = in.nextInt();
      col = in.nextInt();
      if (col >= colDim || row >= rowDim || col < 1 || row < 1)
         throw new Exception("End location must be within maze");
      mEndState = new MazeState(row, col);
   }
}