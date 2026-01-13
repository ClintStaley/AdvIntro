package com.cstaley.stm3911.MMind.V2.ans;

import java.io.IOException;
import java.util.Scanner;

public class Guess {
   private char maxChar;
   private char[] pattern;
   
   public Guess(int numChars, char maxChar) {
      pattern = new char[numChars];
      this.maxChar = maxChar;
   }
   
   /**
    * Constructor for testing: creates a Guess from a String pattern.
    * @param patternStr the pattern string (must match expected length and maxChar)
    * @param maxChar the maximum valid character
    */
   public Guess(String patternStr, char maxChar) {
      this.maxChar = maxChar;
      pattern = patternStr.toUpperCase().trim().toCharArray();
      
      // Validate length and characters
      for (char ch : pattern) {
         if (ch < 'A' || ch > maxChar) {
            assert false : "Invalid character '" + ch 
               + "' in pattern. Must be between A and " + maxChar;
         }
      }
   }   

   public void read(int attempt, Scanner in) throws IOException {
      int index, lineNdx;
      char ch;
      boolean bad;
      String line;

      System.out.printf("Enter guess %2d: ", attempt);
      do {
         line = in.nextLine().trim().toUpperCase();
         
         bad = false;
         for (index = lineNdx = 0; index < pattern.length
            && lineNdx < line.length() && !bad; index++) {

            ch = line.charAt(lineNdx++);
            while (lineNdx < line.length() 
               && Character.isWhitespace(line.charAt(lineNdx)))
               lineNdx++;
            bad = bad || ch < 'A' || ch > maxChar || index < pattern.length;
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
