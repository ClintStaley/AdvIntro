package com.cstaley.stm3911.Stacks3.Answer;

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
      
      System.out.print("Variadic Demo: ");
      ArrStack variadicDemo = new ArrStack();
      
      variadicDemo.push(42, 23, 56, 78, 1, -40);
      while (!variadicDemo.isEmpty()) { 
         System.out.printf("%d ", variadicDemo.top());
         variadicDemo.pop();
      }
      System.out.println();
   }
}

/* Sample runs:

What type of stack do you want: LinkStack
How many integers? 5
Enter 5 integers (but not 42): 10 20 30 40 50
Enter same sequence again: 10 20 30 40 50
50
40
30
20
10

What type of stack do you want: LinkStack
How many integers? 2
Enter 2 integers (but not 42): 40 42
Enter same sequence again: 40 42
Hey, you entered 42.

What type of stack do you want: LinkStack
How many integers? 4
Enter 4 integers (but not 42): 10 20 30 40
Enter same sequence again: 10 20 31 40
Sequences don't match
*/

/* Example for Stack of stacks
Stack< Stack<Integer> > stkOfStack = new ArrStack<Stack<Integer>>();

stk1 = new ArrStack<Integer>();
stk1.push(42); 
stk1.push(12);
stk2 = new ArrStack<Integer>();
stk2.push(45);

stkOfStack.push(stk1);
stkOfStack.push(stk2);
System.out.println(stkOfStack);
*/
