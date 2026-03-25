package com.cstaley.stm3911.MMind.V2.ans;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Session {
   static final int cMaxDim = 10;
   static final char cMaxChar = 'F';
   static final int cMaxCombos = 10000; // Maximum num of possible combinations
   
   private Random rnd;
   private char maxChar;
   private int numChars;
   private String modelType;
   private Scanner input;

   public Session() {
      input = new Scanner(System.in);
      int seed;
      
      while (true) {
         System.out.print("Enter model type (Simple or Counting), max letter, " 
            + "number of letters and seed: ");
         try {
            modelType = input.next();
            maxChar = input.next().charAt(0);
            numChars = input.nextInt();
            seed = input.nextInt();
            input.nextLine();
            if (maxChar < 'A' || maxChar > cMaxChar) { 
               System.out.printf("Max letter must be between A and %c\n",
                  cMaxChar);
            }
            else if (numChars < 1 || numChars > cMaxDim) {
               System.out.printf("Number of letters must be between 1 and %d\n", 
                  cMaxDim);
            }
            else if (Math.pow(maxChar - 'A' + 1, numChars) > cMaxCombos) {
               System.out.printf("More than %d possible combinations\n", 
                  cMaxCombos);
            }
            else if (!modelType.equals("Simple") && !modelType.equals("Counting")) {
               System.out.printf("Model type must be Simple or Counting\n");
            }
            else
               break;
         } catch (Exception err) {
            System.out.print("Bad format for one or more values\n");
            input.nextLine();
         }
      }

      rnd = new Random(seed);
   }
   
   public void run() throws IOException {
      int attempts, allAttempts = 0, games = 0;
      String response;
      Game game;
      
      do {
         games++;
         game = new Game(modelType, numChars, maxChar, rnd);
         attempts = game.play(input);
         allAttempts += attempts;

         System.out.printf("Pattern found in %d attempts.  Current "
            + "average: %6.3f\n\n", attempts, (float)allAttempts / games);
         System.out.print("Another game [Y/N]? ");
         response = input.next().toUpperCase();
         input.nextLine();
      } while (response == "Y");
   }
}
