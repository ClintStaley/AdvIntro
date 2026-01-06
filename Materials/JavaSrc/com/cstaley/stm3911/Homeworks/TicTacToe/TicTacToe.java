package com.cstaley.stm3911.Homeworks.TicTacToe;

import java.util.Scanner;

// Run a TicTacToe game, of any positive dimension > 2.  A game of dimension D
// requires D in a row vertically, horizontally or vertically.
public class TicTacToe {
   
   // Represent game status, with five possibilities
   public enum Status {    // Effectively "class" Status...
      XMove("X moves"),    // ... with only these objects, ever
      OMove("O moves");    // public static Status OMove = new Status("O Moves");
      
      private String name;                  // Description of the status
      
      private Status(String name) {         // Private constructor -- can't use
         this.name = name;                  // outside of Status.
      }
      
      // Used to print the status
      @Override
      public String toString() {
         return name;
      }
   }
   
   public static final int cMinDim = 3;        // At least 3x3
   
   private char[][] board;                     // cDim x cDim array of chars
   private Status status;                      // Track current game status
   private int dim;
   // Probably need at least two more data items
   
   // Create an empty TicTacToe board of the given dimension.  Dim must be at 
   // least 3. 
   public TicTacToe(int dim) {
      assert dim >= cMinDim;
      status = Status.XMove;
      
      // Set up board and fill it with '.'
      
      
   }
   
   // make a Move by placing |symbol| ('x' or 'o') at row/col on the board.  
   // Return true if this is successful.  It may fail due to a symbol that 
   // doesn't match whose move it is, or due to out of bounds row/col, or 
   // because the cell is already taken.
   public boolean makeMove(int row, int col, char symbol) {
      
      // Check for problems and return false if there are any
      if (symbol != 'x' && symbol != 'o' 
       || row < 0 || row >= dim
       || col < 0 || col >= dim || board[row][col] != '.'
       || (status != Status.XMove && symbol == 'x'))
         return false;
      
      // Make the move
      
      // Check for winning patterns resulting from the move, and update status
      // accordingly.
      
      return true;
   }
   
   // 100% EXTRA CREDIT (homework gets 2x points)
   // Remove the most recent move, restoring the board to its prior state.  
   // This function can be done repeatedly, backing all the way up to 
   // original board state.  If board is empty, do nothing.
   //
   // Track the history of moves with a java.util.Stack of objects, of a 
   // class you design.
   public void undoMove() {
      
   }
   
   public Status getStatus() {
      return status;
   }
   
   @Override
   // Return a string that shows the board as a grid of cDim lines with dots at
   // empty spots.  Assuming dim is 3, this might be:
   // x.o
   // .x.
   // oo.
   //
   // Use a StringBuilder to do this.
   public String toString() {
      StringBuilder rtn = new StringBuilder();
      
      // Build a grid of x, o, or . cells, row by row.
      
      return rtn.toString();
   }
   
   // First prompt for a board size, and repeat the prompt until you get an 
   // answer of 3 or more.  Then, accept lines of the form:
   //
   // <x or o> <row> <col>
   //
   // e.g. x 1 2
   //
   // row and col are 0-based.  For each command, perform the move indicated, 
   // or if makeMove returns false, announce it was a bad move.  Then show 
   // the current board and game status (via *implicit* calls to 
   // TicTacToe.toString and Status.toString).  Continue to take commands 
   // until the game is over, or until end of input, whichever is sooner.
   //
   // For the extra credit, accept also the simple command:
   //
   // u
   //
   // for "undo".  Undo the most recent move, and show the board and status 
   // after doing so.
   // 
   // Two sample inputs for the non extra credit case appear below.  Be sure 
   // your code works properly on these, and create at least another one of 
   // your own of similar complexity
   public static void main(String[] args) {
      TicTacToe ttt;
      Scanner in = new Scanner(System.in);
      int dim, row, col;
      char command;

      // Get the board dimension
      
      // Make the TicTacToe object
      
      while (/*not done*/) {
         // Read and perform a command
         System.out.println(ttt);
      }
   }
}

/* Inputs, starting with answers to dimension question
2
3
x 1 1
o 0 1
x 0 2
o 2 0
x 1 0
o 1 2
o 0 0
x 1 2
x 3 2
x 2 3
x 0 0
o 2 2
x 2 1

5
x 0 0
o 4 0
x 1 1
o 4 1
x 2 2
o 4 2
x 3 3
o 4 3
x 4 4

 */
