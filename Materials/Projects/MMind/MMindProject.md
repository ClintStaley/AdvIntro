# MasterMind Project

Starting with the MMind classes from the course examples, make the following modifications and improvements

### Bronze Level

1. Create a Model.java interface that describes the essential nature of a Model: the Matches record and the findMatches method.

2. Take the code for the present Model.java, and make it a SimpleModel class, implementing Model.

3. Create a CountingModel class that extends SimpleModel. CountingModel tracks all patterns that are consistent with the guesses and responses made thus far. It generates all possible patterns in alphabetical order and maintains a list of viable patterns. When getMatches is called, it gets the actual response from its superclass, then filters its viable patterns list to remove any patterns that are inconsistent with that response. CountingModel's toString method reports the count of viable patterns. 

4. Alter the Game class to use a CountingModel, and to display the model (via toString) before each guess, not just at the start of the game.  This will let you see the number of viable patterns at each guess.

#### Submitting Bronze
Submit the Model.java, SimpleModel.java, and CountingModel.java files, plus a snapshot of a run of the MMind application with F 4 1 parameters, that looks like this:

```
Starting game...
1296
Enter guess  1: AABB
1 exact and 0 inexact
256
Enter guess  2: CCDD
1 exact and 1 inexact
52
Enter guess  3: ACED
1 exact and 1 inexact
8
Enter guess  4: BCED
1 exact and 2 inexact
6
Enter guess  5: DCDB
1 exact and 2 inexact
1
Enter guess  6: CDFB
0 exact and 2 inexact
1
Enter guess  7: DEBD
4 exact and 0 inexact
Pattern found in 7 attempts.  Current average:  7.000
```

### Silver Level

Create a LiarsModel class that extends CountingModel. Unlike SimpleModel and CountingModel, LiarsModel does not have a fixed pattern. Instead, it maintains a set of viable patterns that are consistent with all guesses and responses so far. When getMatches is called with a guess, LiarsModel groups all viable patterns by the response they would produce, then returns the response that leaves the most viable patterns remaining. This creates a "liars" game where the model maximizes ambiguity. When multiple response groups have the same maximum count, break ties by preferring the response with the smallest number of exact matches, then (if still tied) prefer the smallest number of inexact matches. The toString method reports the count of viable patterns and the alphabetically lowest pattern in form "%d viable patterns, starting with %s".  LiarsModel also has a getViableCount() method to return the number of viable patterns as an integer.

Augment the supplied LiarsModelTest class.  This includes several complete test configurations.  Fill in 2-3 additional test configurations, including one of similar complexity to the 4x4 case. 

#### Submitting Silver
Submit your LiarsModelTest.java and LiarsModel.java files, along with a run of LiarsModelTest showing all tests passing.

### Platinum Level

Create an OptimalPlayer class that implements an optimal Mastermind solving strategy. OptimalPlayer tracks all viable patterns (patterns consistent with all guesses and responses received so far).  When choosing a guess, it evaluates all possible guesses (not just viable ones, since non-viable guesses can sometimes partition the viable set more evenly) and selects the guess that minimizes the expected number of remaining viable patterns. Use a sum-of-squares metric: for each candidate guess, compute how the viable patterns would be partitioned by their responses, then sum the squares of the partition sizes. Lower sum-of-squares indicates more even partitioning. When multiple guesses have the same score, prefer viable patterns (since these might win immediately). The class must include methods to record responses and update the viable set, check if solved, and get the solution. Include a test main method that repeatedly creates SimpleModel instances with random patterns, uses OptimalPlayer to solve them, and computes the average number of guesses needed across many games.

A correct implementation, averaging across, say, 100 random games, should arrive at 3.7 or fewer guesses per game, on average.

#### Submitting Platinum
Arrange a test demo with me or the course CM.  Once we've approved your code, submit it.