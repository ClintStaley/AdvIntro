package com.cstaley.stm3911.Recursion.Answer;

public class RecursionHW {
   // Compute Fibonacci 
   
   static long[] fibVals = new long[100];
   
   static long fib(int n) {
     long subFib1, subFib2, result;
      
     System.out.printf("Starting fib(%d)\n", n);
     if (fibVals[n] != 0)
        return fibVals[n];
     else if (n < 2)    // 0 and 1 are base cases
         result = n;
     else {
        subFib1 = fib(n-1);
        subFib2 = fib(n-2);
        fibVals[n] = result = subFib1 + subFib2;
     }
     System.out.printf("fib(%d) returns %d\n", n, result);

     return result;
   }

   // Precomputed choose values; initially all zeros
   static long[][] chooseVals = new long[0][0]; // new long[100][100];
 
   static public long choose(int n, int r) {
      long withFirst, withoutFirst, result;
      
      // Question 1: 
      if (r > n/2)
         r = n - r;
      
      // Question 2:
      if (n < chooseVals.length && r < chooseVals[n].length
       && chooseVals[n][r] > 0)
         return chooseVals[n][r];

      System.out.printf("Starting choose(%d, %d)\n", n, r);
      if (r == n || r == 0)
         result = 1;
      else {
         withFirst = choose(n-1, r-1);
         withoutFirst = choose(n-1, r);
         result = withFirst + withoutFirst;
      }

      if (n < chooseVals.length && r < chooseVals[n].length)
         chooseVals[n][r] = result;

      System.out.printf("choose(%d, %d) returns %d\n", n, r, result);
      return result;
   }
   
   static public void main(String[] args) {
      System.out.printf("Choose test: c(5, 3) = %d\n", choose(5,3));
      System.out.printf("Choose test: c(10, 7) = %d\n", choose(10, 7));
      //System.out.printf("Choose test: c(50, 30) = %d\n", choose(50, 30));
      
      //System.out.printf("Choose test 2: c(50, 30) = %d\n", choose(50, 30));
      //System.out.printf("fib(6) = %d", fib(6)); 
      System.out.printf("fib(50) = %d\n", fib(50));
   }
}