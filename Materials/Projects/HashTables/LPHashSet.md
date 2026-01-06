# HashSet (Linear Probing)
Build on the LPHashSet examples from class to do the following:      

1. Design a constructor that accepts an initial size for the table.

1. Write a `toString` method that returns a String giving a diagram of the hash table.  This string has one line per table entry, showing either the table contents and its home bucket index (the one its hash code maps to) in parentheses, `null`, or `wasFull`.

1. Write a `check` function that returns true if every entry is either "null", or `wasFull` or has a single value that either hashes to that location, or hashes to an earlier location for which every intervening value is not `null`.  The number of entries with values must equal `valCount`.

1. Write and test the `remove` function. Be sure your test cases where an item can only be found by traversing past a `wasFull` cell.  

1. Write the `addAll`, `containsAll` and `removeAll` functions, to make it easier to rapidly fill, test, and cull the table.  (You may leave both toArray and the retainAll functions empty.)

1. Write and test the `iterator` function, which returns an iterator that walks through and returns all nonempty elements of the table, from start to end.  (It's a lot easier than the LLHashSet iterator)

1. With the use of `wasFull`, the LPHashSet can reach a point where all of the available spots are `wasFull` instead of `null`.  This forces searches to go all the way around the table if the item they seek isn't there (since there is no `null` to stop their search).  A common solution is to provide a `rehash` method that rebuilds the entire hash table from scratch, starting with all nulls and reinserting each existing item back into the table, to eliminate all `wasFull` entries.  The same method can also increase the table size by making the new table larger than the old one.  Write and test the following method for LPHashTable:

```
// Create a new empty hash table, that is "incr" larger than the original one,
// and rehash all items from the existing hash table into this larger one, discarding
// the old one

public void rehash(int incr)
```

8. It's common practice to avoid filling a linear probing hash table too full, so that at least, say, 1/4 of the entries are `null`.  Revise LPHashSet.add so that it calls rehash, doubling the size of the table, when this threshold is reached.  To make this efficient, track the number of `null` entries in the table as you add/remove items.  Rehash when this number falls below 1/4 of the total entries.

8. Write a main function that thoroughly tests all these functions, including frequent calls of `check` and adding a large number of items so as to trigger a call of `rehash`.  (I may also supply one by this weekend to augment, but not replace, yours.)

## Submit

First, demo your running program to me.  Be sure you've complied with Java style, as provided in our style sheet, and that you have a solid set of test cases in your main.  Once I OK the program, submit you modified LPHashSet.java file.