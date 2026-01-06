package com.cstaley.stm3911.MMind.V2.ans;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
   private Model model;
   private int numChars;
   private char maxChar;
   
   public Game(int numChars, char maxChar, Random rnd) {
      this.numChars = numChars;
      this.maxChar = maxChar;
      model = new Model(numChars, maxChar, rnd);
   }
   
   // Play the game, and return number of attempts required to win
   public int play(Scanner in) throws IOException {
      Model.Matches result;
      Guess guess;
      int whichTry = 0;
      
      guess = new Guess(numChars, maxChar);
      System.out.printf("\nStarting game...\nPattern is %s\n", model);

      do {
         whichTry++;
         guess.read(whichTry, in);
         result = model.findMatches(guess);
         System.out.printf("%d exact and %d inexact\n", result.exact(),
            result.inexact());
      } while (result.exact() < numChars);
      
      return whichTry;
   }

   // Main method test the Game class by playing a game with 4-char, max F
   // and printing the number of attempts required to win.
   public static void main(String[] args) {
      Game game = new Game(4, 'F', new Random(0));

      try {
         Scanner in = new Scanner(System.in);
         System.out.printf("Pattern found in %d attempts\n", game.play(in));
      } catch (IOException e) {
         System.out.println("Error playing game: " + e.getMessage());
      }
   }
}
