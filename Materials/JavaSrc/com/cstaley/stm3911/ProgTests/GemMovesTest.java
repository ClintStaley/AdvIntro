package com.cstaley.stm3911.ProgTests;

import java.util.Scanner;

public class GemMovesTest {  
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      
      int dim = in.nextInt();
      Create and read the array with nextInt calls
      Find and print the possible moves
   }
}

/*
4
3 5 1 10
0 4 11 7
8 13 9 15
2 6 12 14

Move 3 down
Move 8 up
Move 4 left

4
3 5 1 10
4 0 11 7
8 13 9 15
2 6 12 14
Move 5 down
Move 13 up
Move 4 right
Move 11 left
*/
