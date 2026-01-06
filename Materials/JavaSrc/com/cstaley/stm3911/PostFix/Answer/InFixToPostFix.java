package com.cstaley.stm3911.PostFix.Answer;

import java.util.Scanner;

import com.cstaley.stm3911.Stacks3.Answer.LinkStack;
import com.cstaley.stm3911.Stacks3.Answer.Stack;

public class InFixToPostFix {
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
         new OpLevel("-", 1),
         new OpLevel("*", 2),
         new OpLevel("/", 2),
         new OpLevel("(", 3),
      };   
      
      for (OpLevel entry: table) {
         if (op.equals(entry.op))
            return entry.level;
      }
      return -1;
   }
   
   public static void main(String[] args) {
      LinkStack<String> expStk = new LinkStack<String>(); // Stack of subexprs
      Scanner in = new Scanner(System.in);
      String nextOp = "";
      String left, right, op, subExpr;
      
      try {
         do {
            if (in.hasNextDouble())
               expStk.push("" + in.nextDouble());        // String form of double
            else {                            
               nextOp = in.hasNext() ? in.next() : "$";  // Ensure final $
               while (expStk.getSize() > 2 && !expStk.get(1).equals("(") // Reducable
                && getOpLevel(expStk.get(1)) >= getOpLevel(nextOp)) {  
                  right = expStk.pop();
                  op = expStk.pop();
                  left = expStk.pop();
                  subExpr = left + " " + right + " " + op;
                  System.out.println("Reduce to " + subExpr);
                  expStk.push(subExpr);
               }
               if (expStk.getSize() > 1 && expStk.get(1).equals("(") && nextOp.equals(")")) {
                  subExpr = expStk.pop();
                  expStk.pop();           // Dump the (
                  System.out.println("Unbrace " + subExpr);
                  expStk.push(subExpr);
               }
               else
                  expStk.push(nextOp);
            }
         } while (!nextOp.equals("$"));
         
         if (expStk.getSize() == 2)
            System.out.printf("Result is %s\n", expStk.get(1).toString());   
         else
            throw new Exception("Bad expression format");
      }
      catch (Exception err) {
         System.out.println(err);
      }
   }
}
