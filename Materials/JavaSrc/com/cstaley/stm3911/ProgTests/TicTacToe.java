package com.cstaley.stm3911.ProgTests;  // Replace with your package

import java.util.Scanner;

// Run a TicTacToe game, of any positive dimension > 2.  A game of dimension D
// requires D in a row vertically, horizontally or vertically.
public class TicTacToe {
   
   // Represent game status, with five possibilities
   public enum Status {
      XMove("X moves"),
      OMove("O moves"),
      XWins("X wins"),
      OWins("O wins"), 
      Draw("Draw");
      
      private String name;                     // Description of the status
      
      Status(String name) {this.name = name;}
      
      @Override
      public String toString() {return name;}  // Used to print the status
   }
   
   public static final int cMinDim = 3;         // At least 3x3
   
   private int dim;                            // Dimension of the board
   private char[][] board;                     // cDim x cDim array of chars
   private Status status;                      // Track current game status
   private int freeCells;                      // Empty cells remaining
   
   // Create an empty TicTacToe board of size dim.  Dim must be at least 3. 
   public TicTacToe(int dim) {
      assert dim >= cMinDim;
      
      this.dim = dim;
      board = new char[dim][dim];
      freeCells = dim*dim;
      status = Status.XMove;
   }
   
   // make a Move by placing |symbol| ('x' or 'o') at row/col on the board.  Return
   // true if this is successful.  It may fail due to a symbol that doesn't match
   // whose move it is, or due to out of bounds row/col, or because the cell is
   // already taken.
   public boolean makeMove(int row, int col, char symbol) {
      int nwToSe = 0, neToSw = 0;      // Counts for diagonals
      int rowCount = 0, colCount = 0;  // Counts for row and col of new symbol
      
      if (row < 0 || row >= dim || col < 0 || col >= dim 
       || board[row][col] != '\0' || symbol != 'x' && symbol != 'o' 
       || symbol == 'x' && status != Status.XMove 
       || symbol == 'o' && status != Status.OMove)
         return false;
      
      board[row][col] = symbol;
      freeCells--;
      
      for (int idx = 0; idx < dim; idx++) {
         if (board[idx][idx] == symbol)
            nwToSe++;
         if (board[idx][dim - idx - 1] == symbol)
            neToSw++;
         if (board[row][idx] == symbol)
            rowCount++;
         if (board[idx][col] == symbol)
            colCount++;
      }
      
      if (nwToSe == dim || neToSw == dim || rowCount == dim || colCount == dim)         
         status = symbol == 'x' ? Status.XWins : Status.OWins;
      else if (freeCells == 0)
         status = Status.Draw;
      else
         status = symbol == 'x' ? Status.OMove : Status.XMove;
      
      return true;
   }
   
   // Determine if board has equal numbers of X and O, or one more X than O.
   // Also, check that freeCells is correct, and that the game status matches the
   // current board, and that at most one "win" is on the board.
   //
   // Hints: You will need to total up the number of x's and o's per row, col and
   // diagonal.  Use 1-D arrays for the row and col counts.
   // And, count each X as a 1 and each O as a -1, so you can keep just one total
   // per row, col, and diagonal.  A total of dim would mean all X's and a total
   // of -dim means all Os.
   public boolean isGood() {
      int toAdd;                  // Count +1 for each X and -1 for each O
      int allCount = 0, freeCount = 0, nwCount = 0, seCount = 0;
      int[] rowCounts = new int[dim], colCounts = new int[dim];
      int xWins = 0, oWins = 0;   // # of rows, cols or diags on which X or O wins

      return false;
   }
   
   public Status getStatus() {
      return status;
   }
   
   @Override
   // Return a string that shows the board as a grid of cDim lines with dots at
   // empty spots e.g.
   // x.o
   // .x.
   // oo.
   public String toString() {
      StringBuilder rtn = new StringBuilder();
      
      for (int row = 0; row < dim; row++) {
         for (int col = 0; col < dim; col++)
            rtn.append((char)Math.max('.', board[row][col]));
         rtn.append("\n");
      }
      
      return rtn.toString();
   }
   
   public static void main(String[] args) {
      TicTacToe ttt;
      Scanner in = new Scanner(System.in);
      int dim, row, col;
      char command;

      do {
         System.out.print("Enter board size (>2): ");
         dim = in.nextInt();
      } while (dim < cMinDim);
      
      ttt = new TicTacToe(dim);
      
      while ((ttt.getStatus() == Status.XMove || ttt.getStatus() == Status.OMove)
       && in.hasNext()) {
         command = in.next().charAt(0);
         row = in.nextInt();
         col = in.nextInt();
         if (!ttt.makeMove(row, col, command))
            System.out.printf("Bad move %c at %d, %d\n", command, row, col);
         
         System.out.printf("%s\n%s\n\n", ttt, ttt.getStatus());
         System.out.println(ttt.isGood() ? "Good" : "Bad");
         in.nextLine();
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
