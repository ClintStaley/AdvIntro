import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class MMind {
   static final int cMaxDim = 10;
   static final char cMaxChar = 'F';

   static void initialize(char[] model, char maxChar, Random rnd) {
      int index;

      for (index = 0; index < model.length; index++)
         model[index] = (char)('A' + rnd.nextInt(maxChar - 'A' + 1));
   }

   static boolean match(char[] model, char[] guess) {
      int exact = 0, inexact = 0, idx, subIdx;
      char[] temp = new char[model.length];

      for (idx = 0; idx < temp.length; idx++)
         temp[idx] = model[idx];

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

      System.out.printf("    %d exact and %d inexact\n", exact, inexact);

      return exact == model.length;
   }

   static void getGuess(char[] guess, int numChars, char maxChar, int attempt,
    Scanner in) throws IOException {
      int index, lineNdx = 0;
      char ch;
      boolean bad;
      String line;

      System.out.printf("Enter guess %2d: ", attempt);
      do {
         line = in.nextLine().trim().toUpperCase(); // Look up "trim"
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

         bad = bad || index < numChars /* fill in */;
         if (bad)
            System.out.printf("Pattern must have exactly %d characters, "
             + "all between A and %c\nTry again: ", numChars, maxChar);
      } while (bad);
   }

   public static void main(String[] args) {
      int response, games = 0, whichTry, totalTries = 0;
      int numChars = 0, seed = 0;
      Random rnd;
      char maxChar;
      char[] model, guess;
      Scanner input = new Scanner(System.in);

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
               break;
         } catch (Exception err) {
            System.out.print("Bad format for one or more values\n");
            input.nextLine();
         }
      }

      rnd = new Random(seed);
      try {
         do {
            games++;
            model = new char[numChars];
            guess = new char[numChars];
            initialize(model, maxChar, rnd);
            System.out.println("\nStarting game...\nPattern is: "
             + new String(model)); // How do we know we can make a String from model?

            whichTry = 0;
            do {
               whichTry++;
               getGuess(guess, numChars, maxChar, whichTry, input);
            } while (!match(model, guess));
            totalTries += whichTry;

            System.out.printf("Pattern found in %d attempts.  Current "
             + "average: %6.3f\n\n", whichTry, (float)totalTries / games);
            System.out.print("Another game [Y/N]? ");
            response = input.next().toUpperCase().charAt(0);
            input.nextLine();
         } while (response == 'Y');
      } catch (IOException err) {
         System.out.println("Unrecoverable format error");
      }
   }
}