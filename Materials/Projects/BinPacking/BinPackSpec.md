# Bin Packing Project

Write a 2-D binpacking branch-and-bound solution, using the (adjusted) interfaces
from our DFS exercise.  In particular, create classes:

 * BinPacker implementing Problem
 * BinPackerState implementing State
 * BinPackerStep implementing Step
 * BnBSolver implementing Solver, with no code that is specific to bin packing (use the interfaces only, as for DFS)

## Input format
The input of the binpacker, for use by BinPacker.read, is the width of the bin
followed by integer width/heigh pairs, e.g.

10
7 6
9 1
1 8
2 6
4 1
5 1

You may assume no pair's width will exceed that of the bin.  

## Output format
Output is in the form of a total bin height, followed by lower-right corner
placements for each of the items in the input.  The placements must form an
optimal packing, though your particular packing may vary from mine.  Run time must
be comparable to mine.  

8
(9, 1) at (0, 0)
(1, 8) at (9, 0)
(4, 1) at (0, 1)
(5, 1) at (4, 1)
(7, 6) at (0, 2)
(2, 6) at (7, 2)

## Automated test main
I will provide a set of test inputs and outputs to use, but you must also write
a test main program for BinPacker of your own that generates and solves random 
test cases by guillotine cuts as we briefly described in class.  Specifically, 
your test main does the following:

1. Inputs a width and height, a number of items to pack and a loop count, e.g. "10 20 40 5".
Check that the number of items <= width*height and give an error if not.

2. Generate the indicated number of items by starting with a single width x height
item, and randomly cutting it in half along its largest axis.  E.g. a 10x20 item
might be cut into a 10x12 and a 10x8.  Repeat this process until you have the
indicated number of items.  Do this repetition by placing items in a queue, starting
with just the first item, and then repeatedly drawing the first item from the queue,
splitting it randomly as described, and adding both new items to the end of the queue.
If any item drawn from the queue is 1x1, simply place it back on the end without splitting.

3. Add a constructor to BinPacker that takes a width, and a queue created as described, and sets up
the problem according to those data.  Solve the constructed BinPacker problem using BnBSolver, and
confirm that the solution matches the height of the original item before cutting (i.e. that it
reassembles the guillotine cuts into the original shape, though not necessarily in the same order)

4. Run a loop doing steps 2-4 as many times as the input indicated (e.g. 5 in our example). Output a
confirmation that each is correct (or not) as it runs.

This setup will let you create and test sample problems with known optimal solutions, of any size.

## Submit

Demo your test main for me, and let me review your code.  Then submit BinPacker.java and BnBSolver.java.
