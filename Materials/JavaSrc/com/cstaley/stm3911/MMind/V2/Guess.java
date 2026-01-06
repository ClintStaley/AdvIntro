package com.cstaley.stm3911.MMind.V2;

import java.io.IOException;
import java.util.Scanner;

public class Guess {
   private char maxChar;
   private char[] pattern;
   
   public Guess(int numChars, char maxChar) {
      pattern = new char[numChars];
      this.maxChar = maxChar;
   }
   
   // Read a guess from the user, checking for valid input.
   public void read(int attempt, Scanner in) throws IOException {
      int index, lineNdx;
      char ch;
      boolean bad;
      String line;

      bad = false;
      System.out.printf("Enter guess %2d: ", attempt);
      do {
         line = in.nextLine().trim().toUpperCase();

         for (index = lineNdx = 0; index < pattern.length
            && lineNdx < line.length() && !bad; index++) {

            ch = line.charAt(lineNdx++);
            while (lineNdx < line.length() 
               && Character.isWhitespace(line.charAt(lineNdx)))
               lineNdx++;
            bad = bad || ch < 'A' || ch > maxChar;
            pattern[index] = ch;
         }

         bad = bad || index < pattern.length;
         if (bad)
            System.out.printf("Pattern must have exactly %d characters, "
               + "all between A and %c\nTry again: ", pattern.length, maxChar);
      } while (bad);
   }
   
   public char getChar(int idx) {return pattern[idx];}

   public String toString() {return new String(pattern);}

   // Main method test the Guess class by reading a 4-char, max F guess
   // and printing the guess.
   public static void main(String[] args) {
      Guess guess = new Guess(4, 'F');
      Scanner in = new Scanner(System.in);
      int attempt = 1;

      try {
         while (in.hasNextLine()) {
            guess.read(attempt++, in);
            System.out.println(guess);
         }
      } catch (IOException e) {
         System.out.println("Error reading guess: " + e.getMessage());
      }
   }
}
