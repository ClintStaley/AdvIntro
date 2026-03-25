package com.cstaley.stm3911.PostFix.Answer;

import java.util.Scanner;
import java.util.Stack;

public class PostFix {
   public static void main(String[] args) {
      Stack<Double> stk = new Stack<Double>();
      Scanner in = new Scanner(System.in);
      String op;
      double left, right;
      
      try {
         while (in.hasNext()) {
            if (in.hasNextDouble())
               stk.push(in.nextDouble());
            else {
               op = in.next();
               right = stk.pop();
               left = stk.pop();
               if (op.equals("+"))
                  stk.push(left + right);
               else if (op.equals("-"))
                  stk.push(left - right);
               else if (op.equals("*"))
                  stk.push(left * right);
               else if (op.equals("/"))
                  stk.push(left / right);
               else if (op.equals("^"))
                  stk.push(Math.pow(left, right));
               else
                  throw new Exception("Bad operator");
            }
            System.out.println(stk);
         }
         if (stk.size() == 1)
            System.out.printf("Result is %f\n", stk.pop());   
         else
            throw new Exception("Bad expression format");
      }
      catch (Exception err) {
         System.out.println(err);
      }
   }
}
