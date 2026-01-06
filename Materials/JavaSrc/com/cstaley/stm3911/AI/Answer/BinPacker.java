package com.cstaley.stm3911.AI.Answer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.cstaley.stm3911.AI.Problem;

import java.util.List;
import java.util.ArrayList;

public class BinPacker implements Problem {
   // width and height of item
   private static class Item {
      public int mWidth;
      public int mHeight;

      public Item(int width, int height) {
         mWidth = width;
         mHeight = height;
      }

      @Override
      public String toString() {
         return "(" + mWidth + ", " + mHeight + ")";
      }
   }

   // Duplicate type of Item but with the purpose of representing a filled area,
   // no added data.
   private static class Area extends Item {
      public Area(int width, int height) {super(width, height);}
   }

   // Same for locations: a derived class of Item that represents a location in the bin.
   private static class Location extends Item {
      public Location(int width, int height) {super(width, height);}
   }

   // Width of bin and sizes of items to pack
   private int mBinWidth;
   private Item[] mItems;    // Items to place, sorted by decreasing width
   private int mTotalArea;   // Total area of items.
   private State mStartState;

   // State of the problem, representing the filled area of the bin, 
   // from left to right, non-overlapping, and various data on the lowest 
   // area in the filled area.
   private class BinPackerState implements Problem.State {
      private List<Area> mAreas = new ArrayList<>();
      public int mLowIdx;   // Index of the lowest area in mAreas.
      public Area mLowArea; // Lowest area in mAreas.
      public int mStartCol; // Column of the start of the low area.

      // Max height of mAreas.
      private int mUsedHeight;

      // Total area of placed items
      private int mPlacedArea;

      // Total area of mAreas. (mPlacedArea + intentionally empty areas)
      private int mUsedArea;

      // Location of item's lower-left corner if placed, null otherwise.
      private Location[] mInBin;

      // Number of unplaced items.
      private int mUnplacedItems;

      // Index and lower corner of an item to add to bin, along with the
      // cost -- the amount the top of the filled area will rise.
      private class BinPackerStep implements Problem.Step {
         public int mIdx; // Index of item to add or mItems.length if no item.
         public int mRow; // Row of item's lower left corner.
         public int mCol; // Col of item's lower left corner.
         public int mCost; // Amount the top of the filled area will rise.

         public BinPackerStep(int idx, int row, int col) {
            mIdx = idx;
            mRow = row;
            mCol = col;
            mCost = mIdx == mItems.length ? 0
               : Math.max(0, row + mItems[mIdx].mHeight - mUsedHeight);
         }

         @Override
         public int getCost() {
            return mCost;
         }

         @Override
         public String toString() {
            return "(" + (mIdx == mItems.length ? "raise" : mItems[mIdx])
               + " at (" + mCol + ", " + mRow 
               + ") costing " + mCost + ")";
         }
      }

      // Iterate through each unplaced item, and for each item, iterate through
      // each column it can go at in the lowest area.
      private class StepIterator implements Iterator<Problem.Step> {
         public int mEndCol;   // Column past the end of the low area.
         public int mItemIdx;  // Index of the next unplaced item.
         public int mCol;      // Column of the next placement.

         // Set mAreaIdx to the index of the lowest area in mAreas,
         // mItemIdx to -1, then call advance().
         public StepIterator() {
            mEndCol = mStartCol + mLowArea.mWidth;
            mCol = mStartCol-1;
            advance();
         }
         
         private void advance() {
            if (mItemIdx == mItems.length) {
               mItemIdx++;
            }
            else {
               while (mItemIdx < mItems.length && (mInBin[mItemIdx] != null
                  || ++mCol + mItems[mItemIdx].mWidth > mEndCol)) {
                  mItemIdx++;
                  mCol = mStartCol-1;
               }
            }
         }
         
         // Return a BinPackerStep for the next column in which mItems[mItemIdx]
         // can be placed in the low area. If mItemIdx == mItems.length, return
         // a step to indicate that the low area should be raised to the level of
         // its lowest neighbor.
         @Override
         public Problem.Step next() {
            Problem.Step rtn = new BinPackerStep(mItemIdx, mLowArea.mHeight,
               mItemIdx == mItems.length ? mCol+1 : mCol);
            advance();
            return rtn;
         }

         // True even if mItemIdx == mItems.length, since that indicates that
         // the low area should be raised to the level of its lowest neighbor.
         @Override
         public boolean hasNext() {
            return mItemIdx <= mItems.length;
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      }

      // Start with one area of width equal to the bin width and height 0.
      public BinPackerState() {
         mAreas = new ArrayList<>(
            Arrays.asList(mLowArea = new Area(mBinWidth, 0)));
         mInBin = new Location[mItems.length];
         mUnplacedItems = mItems.length;
      }

      // Shallow copy of items and simple data, except for mAreas, which is 
      // a deep copy of the array though not the areas themselves, and mInBin.
      public BinPackerState(BinPackerState state) {
         mAreas = new ArrayList<>(state.mAreas);
         mInBin = state.mInBin.clone();
         mUnplacedItems = state.mUnplacedItems;
         mUsedHeight = state.mUsedHeight;
         mUsedArea = state.mUsedArea;
         mPlacedArea = state.mPlacedArea;
         mStartCol = state.mStartCol;
         mLowArea = state.mLowArea;
         mLowIdx = state.mLowIdx;
      }
      
      @Override
      public Iterator<Step> iterator() {return new StepIterator();}
         
      @Override
      // Assume total remaining area to place can be filled in above the
      // area already placed. Whatever height that is, rounded up,
      // less the current total height, is the distance.
      public int getDistance() {
         return Math.max(0, 
            (int)Math.ceil((mTotalArea - mUsedArea) / mBinWidth) - mUsedHeight);
      }

      // Merge adjacent areas with the same height.  Record the index
      // and area with the lowest height after merging, setting startCol
      // to the starting column of the lowest area.
      private void updateAreas() {
         int sumWidth = 0;

         mStartCol = 0;
         mLowIdx = 0;
         mLowArea = null;
         for (int idx = 0; idx < mAreas.size();) { 
            Area thisArea = mAreas.get(idx);
            // While the next area exists and has the same height as the current
            // area, merge them and remove the next area.
            while (idx < mAreas.size() - 1 && 
               thisArea.mHeight == mAreas.get(idx + 1).mHeight) {
               // Treat Areas as immutable since they are shallow copies.
               mAreas.set(idx, thisArea = new Area(thisArea.mWidth
                  + mAreas.get(idx + 1).mWidth, thisArea.mHeight));
               mAreas.remove(idx + 1);
            }
            if (mLowArea == null || thisArea.mHeight < mLowArea.mHeight) {
               mLowArea = thisArea;
               mLowIdx = idx;
               mStartCol = sumWidth;
            }
            sumWidth += thisArea.mWidth;
            idx++;
         }
      }

      // Apply a step to the state.  The step will place a rectange in the
      // lowest area, or raise the lowest area to the level of its lowest 
      // neighbor.  Placement may entail creation of new areas to the left 
      // and/or right of the lowest area if the placed item is not at the 
      // left or right end of the lowest area.
      public State applyStep(Step step) {
         BinPackerStep bStep = (BinPackerStep)step;
         BinPackerState rtn = new BinPackerState(this);

         if (bStep.mIdx == mItems.length) {
            // Set low area to lowest height of its two neighbors, if they exist
            int lowestNeighbor = mUsedHeight;
            if (mLowIdx > 0)
               lowestNeighbor = Math.min(lowestNeighbor,
                  mAreas.get(mLowIdx - 1).mHeight);
            if (mLowIdx < mAreas.size() - 1)
               lowestNeighbor = Math.min(lowestNeighbor, 
                  mAreas.get(mLowIdx + 1).mHeight);
            rtn.mUsedArea += mLowArea.mWidth * (lowestNeighbor - mLowArea.mHeight);
            rtn.mAreas.set(mLowIdx, new Area(mLowArea.mWidth, lowestNeighbor));
         }
         else {
            int endCol = mStartCol + mLowArea.mWidth;
            Item item = mItems[bStep.mIdx];
            // If we are not at the left end of the low area, insert new area 
            // to the left of the inserted item.
            if (bStep.mCol > mStartCol) {
               rtn.mAreas.add(mLowIdx, new Area(bStep.mCol - mStartCol,
               mLowArea.mHeight));
               rtn.mLowIdx++;
            }
            // Ditto if we are not at the right end of the low area.  Insert
            // new area to the right of the inserted item.
            if (bStep.mCol + item.mWidth < endCol)
               rtn.mAreas.add(rtn.mLowIdx+1,
                  new Area(endCol - (bStep.mCol + item.mWidth),
                  mLowArea.mHeight));
            
            rtn.mAreas.set(rtn.mLowIdx, rtn.mLowArea = new Area(item.mWidth, 
               mLowArea.mHeight + item.mHeight));
            rtn.mUsedArea += item.mWidth * item.mHeight;
            rtn.mPlacedArea += item.mWidth * item.mHeight;
            assert bStep.mCost == Math.max(0, rtn.mLowArea.mHeight - rtn.mUsedHeight);
            rtn.mUsedHeight += bStep.mCost;
            rtn.mInBin[bStep.mIdx] = new Location(bStep.mCol, bStep.mRow);
            rtn.mUnplacedItems--;
         }
         rtn.updateAreas();

         return rtn;
      }
      
      @Override
      // For purposes of avoiding loops in Solvers, check that the number of
      // unplaced items and the locations of the items in the bin are the same.
      public boolean equals(Object rhs) {
         return mUnplacedItems == ((BinPackerState)rhs).mUnplacedItems
            && Arrays.equals(mInBin, ((BinPackerState)rhs).mInBin);
      }

      @Override
      // String representation of the state, including areas as (width, height),
      // and the max height.
      public String toString() {
         StringBuilder rtn = new StringBuilder();
         char[][] grid = new char[mUsedHeight][mBinWidth];
         for (int row = 0; row < mUsedHeight; row++) {
            for (int col = 0; col < mBinWidth; col++) {
               grid[row][col] = ' ';
            }
         }

         // Label items in mInBin with 'A' + index of item.
         for (int idx = 0; idx < mItems.length; idx++) {
            if (mInBin[idx] != null) {
               Location loc = mInBin[idx];
               Item item = mItems[idx];
               for (int row = loc.mHeight; row < loc.mHeight + item.mHeight;
                  row++) {
                  for (int col = loc.mWidth; col < loc.mWidth + item.mWidth;
                     col++) {
                     grid[mUsedHeight-row-1][col] = (char)('A' + idx);
                  }
               }
            }
         }

         // Print the grid.
         for (int row = 0; row < mUsedHeight; row++) {
            for (int col = 0; col < mBinWidth; col++) {
               rtn.append(grid[row][col]);
            }
            rtn.append("\n");
         }

         for (Area area : mAreas) {
            rtn.append(area.toString() + " ");
         }
         rtn.append("\n");
         rtn.append("Max height: " + mUsedHeight + "\n");
         rtn.append("Unplaced items: " + mUnplacedItems + "\n");
         rtn.append("Used area: " + mUsedArea + "\n");
         rtn.append("Placed area: " + mPlacedArea + "\n");
         rtn.append("Low area index: " + mLowIdx + "\n");
         rtn.append("Low area: " + mLowArea.toString() + "\n");
         rtn.append("Start column: " + mStartCol + "\n");
         return rtn.toString();
      }
      
      @Override
      public boolean isGoal() {
         return mUnplacedItems == 0;
      }
   }

   @Override
   public State getStartState() {return mStartState;}

   // Read bin width, and then read items into a List, ending with EOF.
   // From the list, create a BinPackerState and set it as the start state.
   public void read(Scanner in) throws Exception {
      List<Item> items = new ArrayList<>();
      mBinWidth = in.nextInt();
      while (in.hasNextInt())
         items.add(new Item(in.nextInt(), in.nextInt()));
      mItems = items.toArray(new Item[0]);
      Arrays.sort(mItems, (a, b) -> b.mWidth - a.mWidth);
      mTotalArea = 0;
      for (Item item : mItems)
         mTotalArea += item.mWidth * item.mHeight;

      mStartState = new BinPackerState();
   }

   // Read a bin width and items from standard input, and print the start state.
   // Then systematically obtain an iterator on the state, print and apply the
   // first
   // step in that iterator, and repeat, until a goal state is reached.
   public static void main(String[] args) {
      BinPacker binPacker = new BinPacker();
      int limit = 10;
      try {
         binPacker.read(new Scanner(System.in));
         State state = binPacker.getStartState();
         Iterator<Problem.Step> itr = state.iterator();
         while (itr.hasNext() && limit-- > 0) {
            Problem.Step step = itr.next();
            System.out.println(step);
            state = state.applyStep(step);
            System.out.println(state);
            itr = state.iterator();
         }
      }
      catch (Exception err) {
         System.out.println("Error: " + err.getMessage());
         err.printStackTrace();
      }
   }
}

/*
8
1 1
8 1
3 4
4 5
5 3

8
5 2
5 2
5 4
3 1
3 5
3 1

8 
6 1
4 1
3 1
2 2
2 6
2 1



*/