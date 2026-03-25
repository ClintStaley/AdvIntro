# AI Project

### Bronze Level
Complete the Gem puzzle solving program from class, following the design
we discussed.  Here, and in all other levels, use the provided SolvePuzzle.java code and the Problem and Solver interfaces, without modification. 

Adjust the DFSSolver code from class, fixing any omissions and bugs, and use this as your solver engine.

## Input formats
The input of the GemPuzzle problem was already defined in class and in the provided readProblem code.  Here is an example, but you must make more of your own.  And you may of course adjust the package names of the Problem and Solver class
to match yours.

```
com.cstaley.csci182.AI.Answer.GemPuzzle
com.cstaley.csci182.AI.Answer.DFSSolver
15 5

2 3
2 4 3
1 0 5
```

Catch and report any dimensions larger than 5. Report these by throwing suitable exceptions for SolvePuzzle to catch and print.

## Output format
The output for GemPuzzle is a series of paths, in the natural order that results from exploring in north, east, south, west order.  Here is the correct output for the provided input case above.
```
Enter problem type, solution type, max cost and max # of solutions: Enter puzzle problem: 
Answers are: 
Answer 0 with cost 15
   north
   west
   south
   east
   north
   east
   south
   west
   north
   east
   south
   west
   north
   east
   south
Answer 1 with cost 5
   north
   west
   south
   east
   east
Answer 2 with cost 15
   east
   north
   west
   south
   east
   north
   west
   south
   east
   north
   west
   west
   south
   east
   east
Answer 3 with cost 9
   west
   north
   east
   south
   west
   north
   east
   south
   east
```

And here are two more
```
com.cstaley.csci182.AI.Answer.GemPuzzle
 com.cstaley.csci182.AI.Answer.DFSSolver 10 2
4 4
 1 2 3 4
 6 7 0 8
 5 10 15 11
 9 13 14 12
Answers are: 
Answer 0 with cost 9
   west
   west
   south
   south
   east
   east
   north
   east
   south

com.cstaley.csci182.AI.Answer.GemPuzzle
 com.cstaley.csci182.AI.Answer.DFSSolver 4 2
4 4
 1 2 3 4
 6 7 0 8
 5 10 15 11
 9 13 14 12
No solution exists.
```
#### Submitting Bronze
Ensure you get the correct outputs for the three given cases saving these in files Test1-3.in, and the matching output (from your program run, not copied here) in Test1-3.out.  Finally, add another test case of 5x5 dimension, with a known solution of 6 steps that your code properly finds when presented with the problem and a cost limit of 6.  Save this as Test4.in and Test4.out.

Submit Test1-4.in, Test1-4.out, GemPuzzle.java, and DFSSolver.java.

### Silver Level
Write a Sudoku.java file to solve a standard 9x9 Sudoku puzzle.  
The input of the Sudoku problem is as shown here, with zeros for open cells.
```
com.cstaley.csci182.AI.Answer.Sudoku
com.cstaley.csci182.AI.Answer.DFSSolver
530070000
600195000
098000060
800060003
400803001
700020006
060000280
000419005
000080079
```

The output for Sudoku is the 9x9 grid of filled in values (no spaces).  The order of "moves" for solutions from a given board
is in the left to right order of the open cells, and for each cell, in ascending order of possible numbers to place in that cell.  

It is not necessary, and indeed highly inefficient, to iterate across *all* possible moves for a given Sudoku state.  Iterate only across the SudokuSteps possible from the first zero-cell in row-major order, leaving later states to iterate across later zero-cells.  In effect, this is like playing Sudoku where you must fill in open cells from left to right, which is perfectly possible.  The test cases below will assume this order of exploration.

Here is the output for the input just given.
```
534678912
672195348
198342567
859761423
426853791
713924856
961537284
287419635
345286179
```
And here is a second
```
com.cstaley.csci182.AI.Answer.SudokuPuzzle
com.cstaley.csci182.AI.Answer.DFSSolver 80 2
Enter problem: 
 5 3 0 0 7 0 0 0 0
 6 0 0 1 9 5 0 0 0
 0 9 8 0 0 0 0 6 0
 8 0 0 0 0 0 0 0 3
 4 0 0 8 0 3 0 0 1
 7 0 0 0 2 0 0 0 6
 0 6 0 0 0 0 2 8 0
 0 0 0 4 1 9 0 0 5
 0 0 0 0 8 0 0 7 9
Answers are: 
Answer 0 with cost 52
   (0, 2 -> 4)
   (0, 3 -> 6)
   (0, 5 -> 8)
   (0, 6 -> 9)
   (0, 7 -> 1)
   (0, 8 -> 2)
   (1, 1 -> 7)
   (1, 2 -> 2)
   (1, 6 -> 3)
   (1, 7 -> 4)
   (1, 8 -> 8)
   (2, 0 -> 1)
   (2, 3 -> 3)
   (2, 4 -> 4)
   (2, 5 -> 2)
   (2, 6 -> 5)
   (2, 8 -> 7)
   (3, 1 -> 2)
   (3, 2 -> 6)
   (3, 3 -> 7)
   (3, 4 -> 5)
   (3, 5 -> 1)
   (3, 6 -> 4)
   (3, 7 -> 9)
   (4, 1 -> 5)
   (4, 2 -> 9)
   (4, 4 -> 6)
   (4, 6 -> 7)
   (4, 7 -> 2)
   (5, 1 -> 1)
   (5, 2 -> 3)
   (5, 3 -> 9)
   (5, 5 -> 4)
   (5, 6 -> 8)
   (5, 7 -> 5)
   (6, 0 -> 9)
   (6, 2 -> 1)
   (6, 3 -> 5)
   (6, 4 -> 3)
   (6, 5 -> 7)
   (6, 8 -> 4)
   (7, 0 -> 2)
   (7, 1 -> 8)
   (7, 2 -> 7)
   (7, 6 -> 6)
   (7, 7 -> 3)
   (8, 0 -> 3)
   (8, 1 -> 4)
   (8, 2 -> 5)
   (8, 3 -> 2)
   (8, 5 -> 6)
   (8, 6 -> 1)
Answer 1 with cost 52
   (0, 2 -> 4)
   (0, 3 -> 6)
   (0, 5 -> 8)
   (0, 6 -> 9)
   (0, 7 -> 1)
   (0, 8 -> 2)
   (1, 1 -> 7)
   (1, 2 -> 2)
   (1, 6 -> 3)
   (1, 7 -> 4)
   (1, 8 -> 8)
   (2, 0 -> 1)
   (2, 3 -> 3)
   (2, 4 -> 4)
   (2, 5 -> 2)
   (2, 6 -> 5)
   (2, 8 -> 7)
   (3, 1 -> 5)
   (3, 2 -> 9)
   (3, 3 -> 7)
   (3, 4 -> 6)
   (3, 5 -> 1)
   (3, 6 -> 4)
   (3, 7 -> 2)
   (4, 1 -> 2)
   (4, 2 -> 6)
   (4, 4 -> 5)
   (4, 6 -> 7)
   (4, 7 -> 9)
   (5, 1 -> 1)
   (5, 2 -> 3)
   (5, 3 -> 9)
   (5, 5 -> 4)
   (5, 6 -> 8)
   (5, 7 -> 5)
   (6, 0 -> 9)
   (6, 2 -> 1)
   (6, 3 -> 5)
   (6, 4 -> 3)
   (6, 5 -> 7)
   (6, 8 -> 4)
   (7, 0 -> 2)
   (7, 1 -> 8)
   (7, 2 -> 7)
   (7, 6 -> 6)
   (7, 7 -> 3)
   (8, 0 -> 3)
   (8, 1 -> 4)
   (8, 2 -> 5)
   (8, 3 -> 2)
   (8, 5 -> 6)
   (8, 6 -> 1)

com.cstaley.csci182.AI.Answer.SudokuPuzzle
com.cstaley.csci182.AI.Answer.DFSSolver 81 2
Enter problem: 
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
Answers are: 
Answer 0 with cost 81
   (0, 0 -> 1)
   (0, 1 -> 2)
   (0, 2 -> 3)
   (0, 3 -> 4)
   (0, 4 -> 5)
   (0, 5 -> 6)
   (0, 6 -> 7)
   (0, 7 -> 8)
   (0, 8 -> 9)
   (1, 0 -> 4)
   (1, 1 -> 5)
   (1, 2 -> 6)
   (1, 3 -> 7)
   (1, 4 -> 8)
   (1, 5 -> 9)
   (1, 6 -> 1)
   (1, 7 -> 2)
   (1, 8 -> 3)
   (2, 0 -> 7)
   (2, 1 -> 8)
   (2, 2 -> 9)
   (2, 3 -> 1)
   (2, 4 -> 2)
   (2, 5 -> 3)
   (2, 6 -> 4)
   (2, 7 -> 5)
   (2, 8 -> 6)
   (3, 0 -> 2)
   (3, 1 -> 1)
   (3, 2 -> 4)
   (3, 3 -> 3)
   (3, 4 -> 6)
   (3, 5 -> 5)
   (3, 6 -> 8)
   (3, 7 -> 9)
   (3, 8 -> 7)
   (4, 0 -> 3)
   (4, 1 -> 6)
   (4, 2 -> 5)
   (4, 3 -> 8)
   (4, 4 -> 9)
   (4, 5 -> 7)
   (4, 6 -> 2)
   (4, 7 -> 1)
   (4, 8 -> 4)
   (5, 0 -> 8)
   (5, 1 -> 9)
   (5, 2 -> 7)
   (5, 3 -> 2)
   (5, 4 -> 1)
   (5, 5 -> 4)
   (5, 6 -> 3)
   (5, 7 -> 6)
   (5, 8 -> 5)
   (6, 0 -> 5)
   (6, 1 -> 3)
   (6, 2 -> 1)
   (6, 3 -> 6)
   (6, 4 -> 4)
   (6, 5 -> 2)
   (6, 6 -> 9)
   (6, 7 -> 7)
   (6, 8 -> 8)
   (7, 0 -> 6)
   (7, 1 -> 4)
   (7, 2 -> 2)
   (7, 3 -> 9)
   (7, 4 -> 7)
   (7, 5 -> 8)
   (7, 6 -> 5)
   (7, 7 -> 3)
   (7, 8 -> 1)
   (8, 0 -> 9)
   (8, 1 -> 7)
   (8, 2 -> 8)
   (8, 3 -> 5)
   (8, 4 -> 3)
   (8, 5 -> 1)
   (8, 6 -> 6)
   (8, 7 -> 4)
   (8, 8 -> 2)
Answer 1 with cost 81
   (0, 0 -> 1)
   (0, 1 -> 2)
   (0, 2 -> 3)
   (0, 3 -> 4)
   (0, 4 -> 5)
   (0, 5 -> 6)
   (0, 6 -> 7)
   (0, 7 -> 8)
   (0, 8 -> 9)
   (1, 0 -> 4)
   (1, 1 -> 5)
   (1, 2 -> 6)
   (1, 3 -> 7)
   (1, 4 -> 8)
   (1, 5 -> 9)
   (1, 6 -> 1)
   (1, 7 -> 2)
   (1, 8 -> 3)
   (2, 0 -> 7)
   (2, 1 -> 8)
   (2, 2 -> 9)
   (2, 3 -> 1)
   (2, 4 -> 2)
   (2, 5 -> 3)
   (2, 6 -> 4)
   (2, 7 -> 5)
   (2, 8 -> 6)
   (3, 0 -> 2)
   (3, 1 -> 1)
   (3, 2 -> 4)
   (3, 3 -> 3)
   (3, 4 -> 6)
   (3, 5 -> 5)
   (3, 6 -> 8)
   (3, 7 -> 9)
   (3, 8 -> 7)
   (4, 0 -> 3)
   (4, 1 -> 6)
   (4, 2 -> 5)
   (4, 3 -> 8)
   (4, 4 -> 9)
   (4, 5 -> 7)
   (4, 6 -> 2)
   (4, 7 -> 1)
   (4, 8 -> 4)
   (5, 0 -> 8)
   (5, 1 -> 9)
   (5, 2 -> 7)
   (5, 3 -> 2)
   (5, 4 -> 1)
   (5, 5 -> 4)
   (5, 6 -> 3)
   (5, 7 -> 6)
   (5, 8 -> 5)
   (6, 0 -> 5)
   (6, 1 -> 3)
   (6, 2 -> 1)
   (6, 3 -> 6)
   (6, 4 -> 4)
   (6, 5 -> 2)
   (6, 6 -> 9)
   (6, 7 -> 7)
   (6, 8 -> 8)
   (7, 0 -> 6)
   (7, 1 -> 4)
   (7, 2 -> 8)
   (7, 3 -> 9)
   (7, 4 -> 7)
   (7, 5 -> 1)
   (7, 6 -> 5)
   (7, 7 -> 3)
   (7, 8 -> 2)
   (8, 0 -> 9)
   (8, 1 -> 7)
   (8, 2 -> 2)
   (8, 3 -> 5)
   (8, 4 -> 3)
   (8, 5 -> 8)
   (8, 6 -> 6)
   (8, 7 -> 4)
   (8, 8 -> 1)
```
#### Submitting Silver

Submit Suduko.java, Test1-2.in and Test1-2.out giving the two inputs provided and *your* output for them (which must match the outputs given).  Also submit a Test3.in file with all zeros as initial values, a limit of just one solution, and your Test3.out file showing the result.

### Gold Level
write an AStarSolver.java, using BFSSolver.java as a starting point, to solve GemPuzzle with a focus on finding the shortest path to a solution.  You'll need a priority queue for this.  Complete the provided HeapPQueue.java file for this.  Ensure that your AStarSolver prioritizes based on the total of cost thus far incurred (cost of the current path-stack) and the getDistance value estimating remaining cost.

#### Submitting Gold
Create a test case of your own, 5x5, with multiple possible solutions, and demonstrate that it finds solutions in order of their length.  Limit output to 5 solutions.  Submit HeapPQueue.java, AStarSolver.java, Test1.in and Test1.out.

### Platinum Level
1. Implement a branch-and-bound solver, based on optional last-week lecture on Monday and perhaps Tuesday.  This implements Solver, and uses the same interfaces we already have, but assumes that getDistance provides a guaranteed lower bound on the remaining cost, not just an estimate.
2. Build a Problem class to solve a 2-D **rectangle packing** problem.

#### Submitting Platinum 
This one is demo-first.  Set up a demo with me or the course CM.  Be prepared with good test cases; we'll expect you to create a number of them, including ones where you can be sure of the best solution.  Be prepared to discuss your B&B code.

## Hints and Traps to Avoid

1. Do not use System.out.print for debugging!  The logging system will buffer differently from the System.out and you'll get oddly misordered print lines.  Use only log messages for debugging.

2. Start your testing on small GemPuzzle examples, say at most 3x3, and on Sudoku puzzles with only a few empty cells.

