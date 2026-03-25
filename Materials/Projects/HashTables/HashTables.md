# Hash Tables Project

### Bronze Level
Build on the LLHashSet example from class to do the following.

1. Start with the code from class, which should have a constructor, two member data and the `contains`, `add` and `remove`, `size`, and `isEmpty` functions.

1. Fix the missing code in `check` as discussed in class.  Add a `check` call after each iteration of the while-loop in main, printing "ERROR" if it fails.

1. Add a variadic `add` method that accepts an E... parameter.  This will let you
quickly add elements to a hashtable.

1. Augment main, using the small example given as a starting point.  
    
    - Have a command for each method.
    - Create an array of 4 LLHashSets of sizes 1, 4, 8, and 8, which you'll use in testing.
    - Adjust each command to take the form <cmd> <set#> <args>, with `set#` a value 0-3 identifying the hashtable on which to run the command, and `args` as appropriate for the command.  This will let you build input files that set up the hash sets and run commands on them.  For instance:
       - Add several values to set 1:  `addAll 1 42 32 45 -9`
       - Check if set 1 contains 42: `contains 1 42` (prints true)
       - Remove 42 from set 1: `remove 1 42` (prints true since remove succeeds)
       - Check again for 42 `contains 1 42`  (now prints false)
    - For each of the added methods below, supply a main program command.  For methods like `containsAll` that take a Collection, use the already given 
   example, but be sure you understand how it works.  For instance, you might be
   asked to explain how many new List objects `Arrays.asList` creates and what
   other methods are supplied by `Arrays`.

1. Write a `toString` method that returns a String giving a diagram of the hash table. Show one line per table entry, showing `null` or a list of one or more entries at that location, each with its hash code in parentheses.  Add a "print" command to main that calls this toString method (without actually using the name "toString" -- call it implicitly). 

1. Write the `LLItr` class, and the `iterator` function.  `LLItr` tracks both which element it is currently "on" and has a "sub iterator" to track its current position in the LinkedList for that element.  Leave the `LLItr.remove` method unimplemented for now. Include a command `iterate` in your main that iterates through and prints all the elements in a hash table.  Test it on empty and various full tables including ones with several elements in the same bucket.  (Adding at least 5 elements to table 1, the 4-sized table, could do this.)

1. Use an iterator to implement `containsAll`, in just four lines of code.  You can enter a list of strings to make a container of strings in your command interpreter via an approach we'll do in class.  In the test main code, you'll need to read in a series of words, e.g. `containsAll 1 alpha beta gamma`

1. Write the `addAll` and `removeAll` methods.  *Be sure they return the correct boolean value* These should take about 5 lines each.  And, there may come a point where you need to remember that the | and & operators are the same as || and && but *without* short-circuiting behavior.

1. Write the `retainAll` method.  You may find it useful to recall that List already has its own `retainAll`.  This method is a little harder than `addAll` and `removeAll`.

1. Write both `toArray` methods.  The 'toArray' command in the main does the following:
  - Calls `toArray()` to get an array `arr1`
  - Calls `toArray(T[])`, passing it a new array `arr2` of the right size
  - Checks that `arr1` and `arr2` have identical content
  - Checks that the `arr1`'s length equals the size of the table
  - Checks that every element of `arr1` is in the table, via a containsAll call
  - Changes the last element of `arr1` to "NOT HERE" (which presumably is not in the table) and checks that now the containsAll call fails.
  - If any of the above steps fails, prints an error message.

1. Build a good test input file LLHashSetTest.in, including the following
   - Execution of every command including `iterate` and `toString`
   - Test commands, especially `iterate`, on a variety of tables, including an empty one and one that has some empty and some very full buckets.
   - Test commands, especially `iterate`, on a table of one bucket.
   - Test cases where commands like remove or removeAll return both a true and a false.
   - In all, an input file of at least 100 commands is expected.

#### Submitting Bronze

Using the test main you built, run the specified input file, and check that its
output is correct.  Then submit LLHashSet.java, LLHashSetTest.in, and the output file, calling it LLHashSetTest.out

### Silver
Repeat all you did for LLHashSet, but this time for LPHashSet, and with the
following additions:

1. Write `contains`, `remove`, and `reHash`

2. Main creates 4 LPHashSets with sizes 2, 4, 8, and 16.

3. Be sure your LPHastSetTest.in adds 16 items to the size-2 LPHashSet, forcing 4 rehashes in all.

4. toString produces a String with one row per bucket, showing either `<null>` or the sole content of that bucket followed by its hash code in parentheses.

#### Submitting Silver

Submit LPHashSet.java, LPHashSetTest.in, and LPHashSetTest.out after careful checking of the output.

### Gold

1. Add remove to both the LLHashSet and LPHashSet iterators.  Test this with a
`itrRemove` command in `main` that sets up an iterator and then:

  - attempts an iterator remove with no next, catching the resultant exception
  - does a next and then two removes in a row, again catching the exception
  - iterates through the rest of the hash table, doing a `remove` after each `next`, leaving nothing in the table.  (Verify this both by a call of `isEmpty` and by creating a second iterator on the now-empty table and ensuring its `hasNext` immediately returns false)  

2. Tune up LLHashSet by creating a reHash method and doubling the number of buckets (rehashing as needed) when the average number of collision-list traversal steps to reach any item exceeds 2 -- when the average item is at least third in its list.  Think carefully about this rule.  Note, for instance, that it's *not* a matter of the average collision-list length.  An average length of 5 would trigger this case, but *only if all collision-lists had length 5*.  One can get an average list-length of 5 with one bucket of 50 items, and 9 empty buckets.  In such a case, the average number of traversal steps is 24.5.

3. Write a main program to speed-test LPHashSet and your improved LLHashSet:
  - Make one object of each type, using Integer
  - Generate, randomly, an array of 1,000,000 Integers
  - Test each of the hash tables by noting system time in mS, then adding 1,000,000 integers to it, then checking each of them for being in the table, then removing them all via a removeAll call.  Note the system time afterward and report the difference in mS.

#### Submitting Gold
Submit your LLHashSet.java and LPHashSet.java files with the added iterator remove methods and test code for it in the respective files' main methods.  Submit also a HTSpeedTest.java file with a main that performs the system-time based analysis of relative speed, and a SpeedTest.out text file showing the results of that main call.