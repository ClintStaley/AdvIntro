package com.cstaley.stm3911.Stacks3;

import java.util.Scanner;

public class Stacks3 {
   static public void main(String[] args)   {
      int ndx, count;
      String stackType;
      Stack<Integer> stk1, stk2;
      Scanner input = new Scanner(System.in);
            
      System.out.print("What type of stack do you want: ");
      stackType = input.next();
      if (stackType.equals("ArrStack")) { // Why not stackType == "ArrStack"?
         stk1 = new ArrStack<Integer>();
         stk2 = new ArrStack<Integer>();
      }
      else if (stackType.equals("LinkStack")) {
         stk1 = new LinkStack<Integer>();
         stk2 = new LinkStack<Integer>();
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
      
      System.out.printf("Stack 1 has size %d and contents: %s\n",
         stk1.getSize(), stk1);
      System.out.print("Enter same sequence again: ");
      for (ndx = 0; ndx < count; ndx++)
         stk2.push(input.nextInt());

      try {
         if (!stk1.equals(stk2))  // Try this without equals implemented...
            System.out.println("Sequences don't match");
         else if (stk1.isOn(42))
            System.out.println("Hey, you entered 42.");
         else {
            stk1.push(23, 56, 78);
            while (!stk1.isEmpty()) {
               System.out.println(stk1.top());
               stk1.pop();
            }
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

