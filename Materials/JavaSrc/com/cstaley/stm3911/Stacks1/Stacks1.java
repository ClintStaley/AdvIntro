package com.cstaley.stm3911.Stacks1;

import java.util.Scanner;

public class Stacks1 {
   static public void main(String[] args) {
      int ndx, count;
      Stack stk1 = new Stack(), stk2 = new Stack();
      Scanner input = new Scanner(System.in);

      try {
         System.out.print("How many integers? ");
         count = input.nextInt();

         System.out.printf("Enter %d integers (not 42): ", count);
         for (ndx = 0; ndx < count; ndx++)
            stk1.push(input.nextInt());

         System.out.print("Enter same sequence again: ");
         for (ndx = 0; ndx < count; ndx++)
            stk2.push(input.nextInt());

         if (stk1.isOn(42))
            System.out.println("Hey, you entered 42.");
         else if (!stk1.equals(stk2))
            System.out.println("Sequences don't match");
         else {
            stk1.push(20, 30, 40);
            while (!stk1.isEmpty()) {
               System.out.println(stk1.top());
               stk1.pop();
            }
         }
      } 
      catch (IndexOutOfBoundsException e) {
         System.out.println(e.getMessage());
      }
   }
}

/* Sample runs:

How many integers? 5
Enter 5 integers (not 42): 10 20 30 40 50
Enter same sequence again: 10 20 30 40 50
50
40
30
20
10

How many integers? 2
Enter 2 integers (but not 42): 40 42
Enter same sequence again: 40 42
Hey, you entered 42.

How many integers? 4
Enter 4 integers (but not 42): 10 20 30 40
Enter same sequence again: 10 20 31 40
Sequences don't match
*/