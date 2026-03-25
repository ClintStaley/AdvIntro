package com.cstaley.stm3911.Stacks2;

import java.util.Scanner;

public class Stacks2 {
   static public void main(String[] args)   {
      int ndx, count;
      String stackType;
      Stack stk1, stk2;
      Scanner input = new Scanner(System.in);
      
      System.out.print("What type of stack do you want: ");
      stackType = input.next();
      if (stackType.equals("ArrStack")) { // Why not stackType == "ArrStack"?
         stk1 = new ArrStack();
         stk2 = new CountingArrStack();
      }
      else if (stackType.equals("LinkStack")) {
         stk1 = new CountingLinkStack();
         stk2 = new LinkStack();
      }
      else {
         System.out.println("Don't know type " + stackType);
         return;
      }
         
      System.out.print("How many integers? ");
      count = input.nextInt();
      
      System.out.print("Enter " + count + " integers (but not 42): ");
      for (ndx = 0; ndx < count; ndx++)
         stk1.push(input.nextInt());
      
      System.out.print("Enter same sequence again: ");
      for (ndx = 0; ndx < count; ndx++)
         stk2.push(input.nextInt());
      
      System.out.printf("Stack 1 toString result: %s\n", stk1.toString());
      System.out.printf("Stack 2 toString result: %s\n", stk2.toString());

      // If stk1 is LinkStack, downcast and use valAt() to get bottom element
      if (stk1 instanceof LinkStack) {
         LinkStack linkStk1 = (LinkStack)stk1;
         System.out.printf("Bottom element of Stack 1: %d\n", 
            linkStk1.valAt(count - 1));
      }

      try {
         if (!stk1.equals(stk2))  // Try this without equals implemented...
            System.out.println("Sequences don't match");
         else if (stk1.isOn(42))
            System.out.println("Hey, you entered 42.");
         else
            while (!stk1.isEmpty()) {
               System.out.println(stk1.top());
               stk1.pop();
            }
      }
      catch(IndexOutOfBoundsException err) {
         System.out.println("Wow, you're out of bounds");
      }
      catch(Exception err) { 
         System.out.println(err.getMessage());
      }
   }
}
