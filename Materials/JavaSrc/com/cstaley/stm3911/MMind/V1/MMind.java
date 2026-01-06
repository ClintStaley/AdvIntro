package com.cstaley.stm3911.MMind.V1;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class MMind {
   static final int cMaxDim = 10;
   static final char cMaxChar = 'F';

   private char maxChar;
   private int numChars;
   private char[] model;
   private int whichTry;
   private Random rnd;

   MMind(int numChars, char maxChar, int seed) {
      this.numChars = numChars;
      this.maxChar = maxChar;
      this.rnd = new Random(seed);
      this.model = new char[numChars];
      initializeModel();
   }

   private void initializeModel() {
      whichTry = 0;
      for (int index = 0; index < numChars; index++)
         model[index] = (char)('A' + rnd.nextInt(maxChar - 'A' + 1));
   }

   void newGame() {
      initializeModel();
   }

   /*
   Immutable result of comparing a guess to the model.
   Records auto-generate constructor, accessors, equals, hashCode, toString.
   */
   record MatchResult(int exact, int inexact, boolean isWin) {}
   
   /*
   Count exact and inexact matches between model and guess.
   Leave model unchanged; modifying guess is okay.
   */
   MatchResult match(char[] guess) {
      int exact = 0, inexact = 0, idx, subIdx;
      char[] temp = model.clone();

      for (idx = 0; idx < temp.length; idx++) {  
         if (temp[idx] == guess[idx]) {
            guess[idx] = '+';
            temp[idx] = '-';
            exact++;
         }
      }
      
      for (idx = 0; idx < temp.length; idx++)
         for (subIdx = 0; subIdx < guess.length; subIdx++)
            if (temp[idx] == guess[subIdx]) {
               guess[idx] = '+';
               temp[idx] = '-';
               inexact++;
            }

      return new MatchResult(exact, inexact, exact == model.length);
   }

   /*
   Prompt for and return a valid guess. Increments whichTry.
   */
   char[] getGuess(Scanner in) throws IOException {
      char[] guess = new char[numChars];
      int index, lineNdx = 0;
      char ch;
      boolean bad;
      String line;

      whichTry++;
      System.out.printf("Enter guess %2d: ", whichTry);
      do {
         line = in.nextLine().trim().toUpperCase();
         bad = false;

         for (index = lineNdx = 0; index < numChars
            && lineNdx < line.length() && !bad; index++) {
            ch = line.charAt(lineNdx++);
            while (lineNdx < line.length()
               && Character.isWhitespace(line.charAt(lineNdx)))
               lineNdx++;
            bad = bad || (ch < 'A' || ch > maxChar);
            guess[index] = ch;
         }

         bad = bad || index < numChars;
         if (bad)
            System.out.printf("Pattern must have exactly %d characters, "
               + "all between A and %c\nTry again: ", numChars, maxChar);
      } while (bad);

      return guess;
   }

   int getWhichTry() { 
      return whichTry; 
   }

   @Override
   public String toString() { 
      return new String(model); 
   }

   static MMind createGame(Scanner input) {
      int numChars, seed;
      char maxChar;

      while (true) {
         System.out.print("Enter max letter, number of letters and seed: ");
         try {
            maxChar = input.next().charAt(0);
            numChars = input.nextInt();
            seed = input.nextInt();
            input.nextLine();
            if (maxChar < 'A' || maxChar > cMaxChar) {
               System.out.print("Max letter must be between A and F\n");
            }
            else if (numChars < 1 || numChars > cMaxDim) {
               System.out.print("Number of letters must be between 1 and 10\n");
            }
            else
               return new MMind(numChars, maxChar, seed);
         } 
         catch (Exception err) {
            System.out.print("Bad format for one or more values\n");
            input.nextLine();
         }
      }
   }

   public static void main(String[] args) {
      int response, games = 0, totalTries = 0;
      Scanner input = new Scanner(System.in);

      MMind game = createGame(input);

      try {
         do {
            games++;
            System.out.println("\nStarting game...\nPattern is: " + game);

            MatchResult result;
            do {
               char[] guess = game.getGuess(input);
               result = game.match(guess);
               System.out.printf("    %d exact and %d inexact\n", 
                  result.exact(), result.inexact());
            } while (!result.isWin());
            totalTries += game.getWhichTry();

            System.out.printf("Pattern found in %d attempts.  Current "
               + "average: %6.3f\n\n", game.getWhichTry(), 
               (float)totalTries / games);
            System.out.print("Another game [Y/N]? ");
            response = input.next().toUpperCase().charAt(0);
            input.nextLine();
            
            if (response == 'Y')
               game.newGame();
         } while (response == 'Y');
      } catch (IOException err) {
         System.out.println("Unrecoverable format error");
      }
   }
}
