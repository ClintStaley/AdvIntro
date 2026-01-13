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
         new TestConfig.TestStep("AB", 0, 1, 1),
         
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
         new TestConfig.TestStep("ABB", 1, 1, 2),
         
         // Step 3: Guess "BAB"
         // Groups: (3,0):1 (BAB itself), (1,1):1 (BBA)
         // Response (1,1): 1 remaining "BBA"
         new TestConfig.TestStep("BAB", 1, 1, 1),
         
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
         // Response (1,3): 3 remaining, lowest "BDAC"
         new TestConfig.TestStep("DABC", 1, 3, 3),
         
         // Step 7: Guess "BDAC"
         // Response (0,4): 1 remaining, "CADB"
         new TestConfig.TestStep("BDAC", 0, 4, 1),
         
         // Step 8: Guess "CADB"
         // Response (4,0): 1 remaining, "CADB" (solution found)
         new TestConfig.TestStep("CADB", 4, 0, 1),
      }),
   };
   
   /**
    * Runs a test case from a configuration object.
    * Verifies each step: guess produces expected response and 
    * remaining count.
    */
   private static void runTestConfig(TestConfig config) {
      System.out.println("=== Test: " + config.numChars + 
         " chars, maxChar='" + config.maxChar + "' ===");
      LiarsModel model = new LiarsModel(config.numChars, 
         config.maxChar);
      
      int initialCount = (int) Math.pow(config.maxChar - 'A' + 1, 
         config.numChars);
      assert model.getViableCount() == initialCount : 
         "Initial count mismatch: expected " + initialCount + 
         ", got " + model.getViableCount();
      
      // Verify initial alphabetical ordering (first pattern should be 
      // all 'A's)
      String expectedFirst = "A".repeat(config.numChars);
      String actualFirst = model.getAlphabeticallyLowest();
      assert expectedFirst.equals(actualFirst) : 
         "Initial alphabetical ordering: expected '" + 
         expectedFirst + "', got '" + actualFirst + "'";
      
      // Run each test step
      for (int i = 0; i < config.steps.length; i++) {
         TestConfig.TestStep step = config.steps[i];
         Guess guess = new Guess(step.guess(), config.maxChar);
         LiarsModel.Matches response = model.findMatches(guess);
         
         System.out.printf("Step %d: Guess '%s'", i + 1, step.guess());
         System.out.printf("  Response: (%d, %d)", response.exact(), response.inexact());
         System.out.printf("  Remaining: %d (expected: %d)", model.getViableCount(), step.expectedRemainingCount());
         
         assert response.exact() == step.expectedExact() && 
            response.inexact() == step.expectedInexact() :
            "Step " + (i + 1) + " response mismatch: expected (" + 
            step.expectedExact() + ", " + 
            step.expectedInexact() + "), got (" + 
            response.exact() + ", " + response.inexact() + ")";
         assert model.getViableCount() == 
            step.expectedRemainingCount() :
            "Step " + (i + 1) + " remaining count mismatch: " +
            "expected " + step.expectedRemainingCount() + 
            ", got " + model.getViableCount();
      }
      
      System.out.println("✓ PASSED\n");
   }
   
   public static void main(String[] args) {
      try {
         int passed = 0;

         for (TestConfig config : TEST_CONFIGS) {
            try {
               runTestConfig(config);
               passed++;
            } catch (AssertionError e) {
               System.out.println("✗ TEST FAILED: " + e.getMessage());
            } catch (Exception e) {
               System.out.println("✗ ERROR: " + e.getMessage());
               e.printStackTrace();
            }
         }

         if (passed == TEST_CONFIGS.length) {
            System.out.println("✓ All tests passed!");
         }
      } catch (Exception e) {
         System.out.println("✗ FATAL ERROR: " + e.getMessage());
         e.printStackTrace();
         System.exit(1);
      }
   }
}
