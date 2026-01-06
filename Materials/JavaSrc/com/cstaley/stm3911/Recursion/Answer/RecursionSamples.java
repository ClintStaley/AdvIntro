package com.cstaley.stm3911.Recursion.Answer;

public class RecursionSamples {
   
   // Compute factorial of n
   static long fct(int n) {
      long subFct, result;
      
      //System.out.printf("Starting fct(%d)\n", n);
      if (n <= 1)            // Can we tighten this?
         result = 1;
      else {
         subFct = fct(n-1);  // Compute (n-1)*(n-2)*... 2*1
         result = n*subFct;  // Multiply in the final n
      }
      //System.out.printf("fct(%d) returns %d\n", n, result);
      
      return result;
   }
   
   // Terse version
   // static int fct(n) {return n <= 1 ? 1 : n*fct(n-1);}
   
   // Compute Fibonacci 
   static int fib(int n) {
      int subFib1, subFib2, result;
      
     //System.out.printf("Starting fib(%d)\n", n);
     if (n < 2)    // 0 and 1 are base cases
         result = n;
     else {
        subFib1 = fib(n-1);
        subFib2 = fib(n-2);
        result = subFib1 + subFib2;
     }
     //System.out.printf("fib(%d) returns %d\n", n, result);

     return result;
   }
   
   // static int fib(n) {return n <= 2 ? n : fib(n-1) + fib(n-2);}
   
   // Compute n choose r
   static public int choose(int n, int r) {
      int withFirst, withoutFirst, result;

      System.out.printf("Starting choose(%d, %d)\n", n, r);
      if (r == n) // Can add || r == 0 and eliminate second case
         result = 1;
      else if (r == 1)
         result = n;
      else {
         withFirst = choose(n-1, r-1);
         withoutFirst = choose(n-1, r);
         result = withFirst + withoutFirst;
      }
      System.out.printf("choose(%d, %d) returns %d\n", n, r, result);
      
      return result;
   }
   
   
   
   static public void main(String[] args) {
      //System.out.printf("Fct tests fct(2) = %d fct(5) = %d\n", fct(2), fct(5));
      //System.out.printf("Fib tests fib(2) = %d fib(5) = %d\n", fib(2), fib(5));
      
      //System.out.printf("Big fct call: %d\n", fct(20));
      //System.out.printf("Big fib call: %d\n", fib(42));  // Try increasing gradually
      
      System.out.printf("Choose tests: c(4,2) = %d c(5,3) = %d\n",
       choose(4,2), choose(5,3));
   }
}
