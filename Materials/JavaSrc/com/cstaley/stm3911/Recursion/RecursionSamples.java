package com.cstaley.stm3911.Recursion;

public class RecursionSamples {

   // Compute factorial of n
   static long fct(int n) {
      long subFct, result;

      System.out.printf("Starting fct(%d)\n", n);
      if (n == 0) // Can we tighten this?
         result = 1;
      else {
         subFct = fct(n - 1); // Compute (n-1)*(n-2)*... 2*1
         result = n * subFct; // Multiply in the final n
      }
      System.out.printf("fct(%d) returns %d\n", n, result);

      return result;
   }

   // Terse version
   // static int fct(n) {return n <= 1 ? 1 : n*fct(n-1);}

   // Compute Fibonacci
   static int fib(int n) {
      int subFib1, subFib2, result;

      System.out.printf("Starting fib(%d)\n", n);
      if (n < 2) // 0 and 1 are base cases
         result = n;
      else {
         subFib1 = fib(n - 1);
         subFib2 = fib(n - 2);
         result = subFib1 + subFib2;
      }
      System.out.printf("fib(%d) returns %d\n", n, result);

      return result;
   }

   // Compute n choose r
   static public int choose(int n, int r) {
      int chooseFirst, skipFirst, result;

      System.out.printf("Starting choose(%d, %d)\n", n, r);
      if (n == r || r == 0)
         result = 1;
      else {
         chooseFirst = choose(n - 1, r - 1);
         skipFirst = choose(n - 1, r);
         result = chooseFirst + skipFirst;
      }
      System.out.printf("choose(%d, %d) returns %d\n", n, r, result);

      return result;
   }

   static public void main(String[] args) {
      //System.out.printf("Fct tests fct(2) = %d fct(5) = %d\n", fct(2), fct(5));
      //System.out.printf("Fib tests fib(2) = %d fib(5) = %d\n", fib(2), fib(5));

      System.out.printf("Big fct call: %d\n", fib(30));
      //System.out.printf("Big fib call: %d\n", fib(52)); // Try increasing gradually

      //System.out.printf("Choose tests: c(4,2) = %d c(5,3) = %d\n",
    //        choose(4, 2), choose(5, 3));
   }
}
