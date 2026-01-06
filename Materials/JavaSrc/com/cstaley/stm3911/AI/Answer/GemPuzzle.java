package com.cstaley.stm3911.AI.Answer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.cstaley.stm3911.AI.Problem;

public class GemPuzzle implements Problem {
   public static final int MAX_DIM = 10;
   
   private static enum GemStep implements Problem.Step {
      north(-1, 0),
      east(0, 1),
      south(1,0), 
      west(0, -1);
      
      @Override
      public int getCost() {return 1;}
      
      public int mRowDiff;
      public int mColDiff;
      private GemStep(int r, int c) {mRowDiff = r; mColDiff = c;}
   }
   
   static GemStep[] mSteps = GemStep.values();
      
   private class GemState implements Problem.State {
      private class GemIterator implements Iterator<Problem.Step> {
         int mNext;

         public GemIterator() {
            mNext = -1;
            advance();
         }
         
         // Advance mNext to next valid GemStep, or to mSteps.length
         private void advance() {
            GemStep step;
            int newRow, newCol;
            
            while (true) {
               if (++mNext == mSteps.length)
                  break;
               step = mSteps[mNext];
               
               newRow = mRow + step.mRowDiff;
               newCol = mCol + step.mColDiff;

               if (newRow >= 0 && newRow < mRowDim
                && newCol >= 0 && newCol < mColDim)
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
      
      private byte[] mBoard; // Content of board, in row-major order
      private int mRow;      // Row of open spot
      private int mCol;      // Col of open spot

      @Override
      public Iterator<Step> iterator() {return new GemIterator();}
      
      @Override
      public State applyStep(Step step) {
         GemStep gStep = (GemStep)step;
         GemState rtn = new GemState();
         int newSpace;
         
         rtn.mRow = mRow + gStep.mRowDiff;
         rtn.mCol = mCol + gStep.mColDiff;

         newSpace = rtn.mRow*mColDim + rtn.mCol;
         rtn.mBoard = mBoard.clone();
         rtn.mBoard[mRow*mColDim + mCol] = rtn.mBoard[newSpace];
         rtn.mBoard[newSpace] = 0;
         
         return rtn;
      }
      
      @Override
      public String toString() {
         StringBuilder rtn = new StringBuilder();
         
         for (int row = 0; row < mRowDim; row++) {
            for (int col = 0; col < mColDim; col++)
               rtn.append(" " + mBoard[row*mColDim + col]);
            rtn.append("\n");
         }
         return rtn.toString();
      }
      
      @Override
      public boolean equals(Object rhs) {
         return Arrays.equals(mBoard, ((GemState)rhs).mBoard);
      }
   }

   private GemState mStartState;
   private int mRowDim;            // Row dimensions of puzzle
   private int mColDim;            // Col dimensions of puzzle

   @Override
   public State getStartState() {return mStartState;}

   @Override
   public int getDistance(State st) {
      GemState gState = (GemState)st;
      int ndx, dist = 0, rDiff, cDiff;
      
      for (ndx = 0; ndx < gState.mBoard.length; ndx++) {
         if (gState.mBoard[ndx] != 0 && gState.mBoard[ndx]-1 != ndx) {
            rDiff = (gState.mBoard[ndx]-1) / mColDim - ndx / mColDim;
            cDiff = (gState.mBoard[ndx]-1) % mColDim - ndx % mColDim;
            dist += (rDiff > 0 ? rDiff : -rDiff) + (cDiff > 0 ? cDiff : -cDiff);
         }
      }
            
      return dist;
   }

   public void read(Scanner in) throws Exception {
      byte[] dup;
      GemState state = new GemState();
      
      mRowDim = in.nextInt();
      mColDim = in.nextInt();
      if (mRowDim > MAX_DIM || mRowDim < 1
       || mColDim > MAX_DIM || mColDim < 1)
         throw new Exception("Dimensions must be between 1 and " + MAX_DIM);
      
      state.mBoard = new byte[mRowDim*mColDim];
      for (int ndx = 0; ndx < state.mBoard.length; ndx++)
         if ((state.mBoard[ndx] = in.nextByte()) == 0) {   // If open space
            state.mRow = ndx / mColDim;
            state.mCol = ndx % mColDim;
         }
      
      dup = (byte[])state.mBoard.clone();
      Arrays.sort(dup);
      for (int ndx = 0; ndx < dup.length; ndx++)
         if (dup[ndx] < ndx)
            throw new Exception("Board repeats value " + dup[ndx]);
         else if (dup[ndx] > ndx)
            throw new Exception("Board skips value " + ndx);
      
      mStartState = state;
   }
}

/*
 1 2 3 4
 5 6 7 8
 9 10 15 11
 13 0 14 12
 * 
 */