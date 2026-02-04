package com.cstaley.stm3911.MMind.V2.ans;

public class LiarsModelTest {
   /**
    * Configuration for a LiarsModel test case.
    * Contains the initial parameters (numChars and maxChar) and a 
    * sequence of test steps (guesses with expected responses and 
    * remaining counts).
    */
   public static class TestConfig {
      /**
       * Represents a single step in a test case: a guess, expected 
       * response, and expected remaining viable patterns.
       */
      public static record TestStep(String guess, int expectedExact, 
         int expectedInexact, int expectedRemainingCount) {}
      
      public final int numChars;
      public final char maxChar;
      public final TestStep[] steps;
      
      public TestConfig(int numChars, char maxChar, TestStep[] steps) {
         this.numChars = numChars;
         this.maxChar = maxChar;
         this.steps = steps;
      }
   }
   
   /**
    * Static array of test configurations.
    * Each configuration tests a specific scenario with a sequence of 
    * guesses and verifies the expected responses and remaining viable 
    * patterns.
    */
   private static final TestConfig[] TEST_CONFIGS = {
      // Test 1: Warmup 2x2 case (length=2, maxChar='B')
      // Simplest case: 2^2 = 4 patterns (AA, AB, BA, BB)
      new TestConfig(2, 'B', new TestConfig.TestStep[] {
         // Step 1: Guess "AA"
         // Groups: (2,0):1 (AA itself), (1,0):2 (AB, BA), (0,0):1 (BB)
         // Max count 2, response (1,0): 2 remaining (AB, BA)
         new TestConfig.TestStep("AA", 1, 0, 2),
         
         // Step 2: Guess "AB"
         // Groups: (2,0):1 (AB itself), (0,1):1 (BA - A inexact, B inexact)
         // Response (0,1): 1 remaining "BA"
         new TestConfig.TestStep("AB", 0, 2, 1),
         
         // Step 3: Guess "BA"
         // Response (2,0): 1 remaining "BA" (solution found)
         new TestConfig.TestStep("BA", 2, 0, 1),
      }),
      
      // Test 2: Warmup 3x2 case (length=3, maxChar='B')
      // Simpler case: 2^3 = 8 patterns (AAA, AAB, ABA, ABB, BAA, BAB, 
      // BBA, BBB)
      new TestConfig(3, 'B', new TestConfig.TestStep[] {
         // Step 1: Guess "AAA"
         // Groups: (3,0):1, (2,0):3, (1,0):3, (0,0):1
         // Max count 3, tie between (2,0) and (1,0), tie-break picks 
         // (1,0) - smallest exact
         // Response (1,0): 3 remaining (ABB, BAB, BBA)
         new TestConfig.TestStep("AAA", 1, 0, 3),
         
         // Step 2: Guess "ABB"
         // Groups: (3,0):1 (ABB itself), (1,1):2 (BAB, BBA)
         // Response (1,1): 2 remaining (BAB, BBA)
         new TestConfig.TestStep("ABB", 1, 2, 2),
         
         // Step 3: Guess "BAB"
         // Groups: (3,0):1 (BAB itself), (1,1):1 (BBA)
         // Response (1,1): 1 remaining "BBA"
         new TestConfig.TestStep("BAB", 1, 2, 1),
         
         // Step 4: Guess "BBA"
         // Response (3,0): 1 remaining "BBA" (solution found)
         new TestConfig.TestStep("BBA", 3, 0, 1),
      }),
      
      // Test 3: Complete 4x4 case (length=4, maxChar='D')
      // Exhaustive analysis from initial state through solution
      // Initial: 4^4 = 256 patterns
      new TestConfig(4, 'D', new TestConfig.TestStep[] {
         // Step 1: Guess "AAAA"
         // Response (1,0): 108 remaining, 
         new TestConfig.TestStep("AAAA", 1, 0, 108),
         
         // Step 2: Guess "AAAA" again
         // Response (1,0): 108 remaining (no change)
         new TestConfig.TestStep("AAAA", 1, 0, 108),
         
         // Step 3: Guess "BBBB"
         // Response (1,0): 24 remaining (4*3*2*2) = 48
         new TestConfig.TestStep("BBBB", 1, 0, 48),
         
         // Step 4: Guess "CCCC"
         // Response (1,0): 24 remaining (all permutations of ABCD)
         new TestConfig.TestStep("CCCC", 1, 0, 24),
         
         // Step 5: Guess "ABCD"
         // Response (0,4): 9 remaining (9 derangements of ABCD)
         new TestConfig.TestStep("ABCD", 0, 4, 9),
         
         // Step 6: Guess "DABC"
         // Response (1,3): 4 remaining, lowest "BDAC"
         new TestConfig.TestStep("DABC", 1, 3, 4),
         
         // Step 7: Guess "BDAC"
         // Response (1,3): 2 remaining, "CDBA"
         new TestConfig.TestStep("BDAC", 1, 3, 2),
         
         // Step 8: Guess "CDBA"
         // Response (0,4): 1 remaining, "DCAB" (solution found)
         new TestConfig.TestStep("DCAB", 0, 4, 1),

         // Step 9: Guess "DCAB"
         // Response (4,0): 1 remaining, "CDBA" (solution found)
         new TestConfig.TestStep("CDBA", 4, 0, 1),
      }),
   };
   
   /**
    * Runs a test case from a configuration object.
    * Verifies each step: guess produces expected response and 
    * remaining count.
    * @return number of errors found in this test
    */
   private static int runTestConfig(TestConfig config) {
      int errors = 0;
      System.out.println("=== Test: " + config.numChars + 
         " chars, maxChar='" + config.maxChar + "' ===");
      LiarsModel model = new LiarsModel(config.numChars, 
         config.maxChar);
      
      int initialCount = (int) Math.pow(config.maxChar - 'A' + 1, 
         config.numChars);
      if (model.getViableCount() != initialCount) {
         System.out.println("ERROR: Initial count mismatch: expected " + 
            initialCount + ", got " + model.getViableCount());
         errors++;
      }
      
      // Run each test step
      for (int i = 0; i < config.steps.length; i++) {
         TestConfig.TestStep step = config.steps[i];
         Guess guess = new Guess(step.guess(), config.maxChar);
         Model.Matches response = model.getMatches(guess);
         
         System.out.printf("Step %d: Guess '%s'", i + 1, step.guess());
         System.out.printf("  Response: (%d, %d)", response.exact(), 
            response.inexact());
         System.out.printf("  Remaining: %d (expected: %d)", 
            model.getViableCount(), step.expectedRemainingCount());
         
         if (response.exact() != step.expectedExact() || 
            response.inexact() != step.expectedInexact()) {
            System.out.println();
            System.out.println("ERROR: Step " + (i + 1) + 
               " response mismatch: expected (" + 
               step.expectedExact() + ", " + 
               step.expectedInexact() + "), got (" + 
               response.exact() + ", " + response.inexact() + ")");
            errors++;
         }
         if (model.getViableCount() != step.expectedRemainingCount()) {
            System.out.println();
            System.out.println("ERROR: Step " + (i + 1) + 
               " remaining count mismatch: expected " + 
               step.expectedRemainingCount() + ", got " + 
               model.getViableCount());
            errors++;
         } else {
            System.out.println();
         }
      }
      
      if (errors == 0) {
         System.out.println("✓ PASSED\n");
      } else {
         System.out.println("✗ FAILED with " + errors + " error(s)\n");
      }
      
      return errors;
   }
   
   public static void main(String[] args) {
      int totalErrors = 0;

      try {
         for (TestConfig config : TEST_CONFIGS) {
            try {
               totalErrors += runTestConfig(config);
            } catch (Exception e) {
               System.out.println("✗ ERROR: " + e.getMessage());
               e.printStackTrace();
               totalErrors++;
            }
         }

         System.out.println("Total errors: " + totalErrors);
         if (totalErrors == 0) {
            System.out.println("Passed");
         } else {
            System.out.println("Failed");
         }
      } catch (Exception e) {
         System.out.println("✗ FATAL ERROR: " + e.getMessage());
         e.printStackTrace();
         System.exit(1);
      }
   }
}
