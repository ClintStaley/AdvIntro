package com.cstaley.stm3911.PostFix;

import java.util.Scanner;

import com.cstaley.csci182.Stacks3.Stack;
import com.cstaley.csci182.Stacks3.LinkStack;

public class InFixToPostFixClass {
   private static class OpLevel {
      public String op;
      public int level;
      public OpLevel(String op, int level) {this.op = op; this.level = level;}
   }
   
   private static int getOpLevel(String op) {
      OpLevel[] table = {
         new OpLevel("$", 0),  // Lower values force closure of equal or higher ones
         new OpLevel(")", 0),
         new OpLevel("+", 1),
         new OpLevel("*", 2),
         new OpLevel("(", 3),
         // Add two more 
      };   
      
      for (OpLevel entry: table) {
         if (op.equals(entry.op))
            return entry.level;
      }
      return -1;
   }
   
   public static void main(String[] args) {
      Stack<String> expStk = new LinkStack<String>(); // Stack of subexprs
      Scanner in = new Scanner(System.in);
      String nextOp = "";
      String left, right, op, subExpr;
      
      try {
         do {
            if (in.hasNextDouble())
               expStk.push("" + in.nextDouble());        // String form of double
            else {                            
               nextOp = in.hasNext() ? in.next() : "$";  // Ensure final $
               while (expStk.getSize() > 2 &&  // Reducible operator ??
                && getOpLevel(expStk.get(1)) >= getOpLevel(nextOp)) {  
                  right = expStk.pop();
                  op = expStk.pop();
                  left = expStk.pop();
                  subExpr = left + " " + right + " " + op;
                  System.out.println("Reduce to " + subExpr);
                  expStk.push(subExpr);
               }
               if (/* Pair of parentheses surrounding a subexpr*/) {
                  subExpr = expStk.pop();
                  // Another step here... 
                  System.out.println("Unbrace " + subExpr);
                  expStk.push(subExpr);
               }
               else
                  expStk.push(nextOp);
            }
         } while (!nextOp.equals("$"));
         
         if (/*All well*/)
            System.out.printf("Result is %s\n", expStk.get(1).toString());   
         else
            throw new Exception("Bad expression format");
      }
      catch (Exception err) {
         System.out.println(err);
      }
   }
}
