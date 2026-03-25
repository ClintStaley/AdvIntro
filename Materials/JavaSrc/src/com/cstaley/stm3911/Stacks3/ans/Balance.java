package com.cstaley.stm3911.Stacks3.ans;
import java.util.Scanner;

public class Balance {

   // Return Character representing the matching pair for c, or null if no match.
   private static Character getMatch(char c, char[][] pairs) {
      for (char[] pair : pairs) {
         if (pair[0] == c) {
            return pair[1];
         }
      }
      return null;
   }

   private static boolean isCloser(char c, char[][] pairs) {
      for (char[] pair : pairs) {
         if (pair[1] == c) {
            return true;
         }
      }
      return false;
   }

   // pairs is an nx2 array of characters, with each row representing a
   // balanced pair, e.g. [['(',')'], ['{','}'], ... ]. Return true iff text is
   // properly balanced for the character-pairs in pairs. Be sure this works even
   // if test is 0 length.  Use a LinkStack to track pairs
   static boolean isBalanced(String text, char[][] pairs) {
      LinkStack<Character> stack = new LinkStack<Character>();
      for (char c : text.toCharArray()) {
         Character partner = getMatch(c, pairs);
         if (partner != null) {
            stack.push(partner);
         } 
         else { 
            if (isCloser(c, pairs) && (stack.isEmpty() || stack.pop() != c)) 
               return false;
         }
      }
      return stack.isEmpty();
   }

   // Test main program first reads a line comprising matching pairs, e.g.
   // {}[]**()
   // It then reads as many lines of text as the user wants to enter, and for
   // each line, it prints whether the line is balanced or not.
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      String pairs = input.nextLine();
      char[][] pairsArray = new char[pairs.length() / 2][2];
      for (int i = 0; i < pairs.length(); i += 2) {
         pairsArray[i / 2][0] = pairs.charAt(i);
         pairsArray[i / 2][1] = pairs.charAt(i + 1);
      }

      while (input.hasNextLine()) {
         String text = input.nextLine();
         System.out.println(isBalanced(text, pairsArray) ?
            "balanced" : "imbalanced");
      }
   }
}

/* Test data
(){}[]""''+-
(Hello[{+-}])
"test*"
(([a]b)c))
*/
