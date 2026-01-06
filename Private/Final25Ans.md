# Advanced Intro Final

Name_________________________________________________

## 1 66pts Vocabulary

Object referred to by `this` which is is implicitly passed as a parameter **receiver object**

Term for the rule that makes upcasts automatically allowed **ISA**

Max value you can count up to in 32 bits (unsigned).  An approximate value is OK. **4 billion**

Alternate term for "base class" **superclass**

Resolving collisions in a hash table by looking downward for the next empty spot **linear probing**

Resolving collisions in a hash table by squaring the amount you step each time, to avoid clustering **quadratic probing**

AI state-space search that operates like the TreeSet iterator **DFS**

Implementing this interface makes your class work with a for-each loop **Iterable**

Area of RTS that contains information for one currently-running function call **stack frame or activation record**

Memory sequence of elements typical for a 2-D array in modern tensor libraries **row-major order**

General term for the "wrap around" memory order of our ArrQueue class **circular buffer**

Saving the results of prior recursive calls to avoid repetition of calculation **memoization**

Recursive case that does *not* make a recursive call. **base case**

Three-word term for property indicated by big-O form, e.g. O(nlogn) **order of complexity**

Term for O(n) **linear complexity**

Commonly used sorting method that has O(n^2) (n-squared) behavior **insertion sort**

Use of inheritance in which you refine or extend an already working base class.
**implementation inheritance**

Interface that describes a type that can self-compare, via a compareTo method **Comparable**

Language feature that permits several functions to have the same name.
**function overloading**

Pseudo machine language into which Java source is translated. **bytecode**

Constructor that requires no arguments **default constructor**

The square root of 16384 **128**

Class that is designed to hold an elemental type in order to make it an object **wrapper class**

Runtime memory area from which `new` gets its storage.  **runtime heap**

One location in a hash table **bucket**

Interface that defines an object usable to compare other objects **Comparator**

Any class that holds many values of some other type **container class**

Element chosen as the dividing value in a quicksort partition **pivot**

Sorting algorithm that repeated scans unsorted elements to find the minimum, and moves it down when found **selection sort**

Type of binary tree resulting from addition of all values in already-sorted order **degenerate**

Term for a tree node that has no children **leaf**

In Numpy and similar libraries, repetition along a dimension to extend it
from 1 to match the same dimension in another tensor. **broadcasting**

Programming language in which Numpy internals are actually implemented **C or C++**


## 2. 24pts Numpy
For each of the prints in this numpy code, show the output.  Be careful to use the right number of braces for any 2-D output.

```
array = np.array([[1,2,3], [4,5,6]])
print(array.shape)             **(2,3) 4pts**

array = np.maximum(np.minimum(array, 4), 2)
print(array)                   **[[2,2,3], [4,4,4]] 8pts**

array = np.dot(array, array.T)  # (transpose)
print(array)                   **[[17, 28], [28, 48]] 12pts**
```
## 3. 34pts Stacks
### a. 16pts 
Fill in this function for LinkStack, following the comments:

```
// Return true iff the top two items on the stack exist (stack has at least two
// elements) and they have the same data.  Do this with just the single return 
// statement -- a boolean expression not using ?:.  Do NOT call any other stack methods.
boolean firstTwoSame() {

   return ________________________________________________________________;
}
```
**head != null 4pts && head->next != null 4pts && head.data.equals(head.next.data) (8pts)**

### b. 18pts 
Fill in this function for LinkStack, following the comments, and adding
code only at the blank spaces:

```
// Pop the top n values off the stack, or fewer if there are not n values.
// Importantly, do this without calling any other method, and by a *single*
// assignment after the loop (no assignments in the loop).
void popMultiple(int n) {
   Node temp;

   for (temp = head; ________________ && _______________; temp = temp.next)
      ;

   _____________________________;
}
```

```
   for (temp = head; temp != null (4pts) && n-- > 0 (8pts); temp = temp.next)
      ;

   head = temp; (6pts)
```

## 4. 76pts Sorting Concepts
### a. 12pts 
For an array of 8 elements, what is the maximum number of times that insertion sort's *inner* loop could run (the maximum total executions of that loop's body across the entire run)?  What array contents would cause this? (Hint: the value is < 32))

**7 summation or 28. 8pts  Purely descending order 4pts**

### b. 12pts 
For an array of 10 elements, what is the maximum number of memory *write* operations into the array that **selection sort** could do and why?  What is the minimum number?

**Max number is 9 swaps x 2 writes/swap for 18 writes 10pts (20 or 10 6pts).  Min
is zero 2pts**

### c. 32pts 

Consider the quicksort partition code below, and answer the following:

i. 6pts Why is there no `else` for the `if`?  What variable must be updated if the if is false, and how does this occur?

**No else because value is already on high side (2pts) toPlace must increase (2pts), but this is automatic in loop (2pts)**

ii. 6pts If all values are the same, say all equal to 42, how will the array be divided into hi and low parts?  Is this OK?  Why or why not?

**Will all gather on high side since none meet < pivot requirement 3pts. Bad since causes imbalanced partition 3pts (Bad due to endless recursion 0pts)**

iii. 4pts Would changing `< pivot` to `<= pivot` help in the situation of all-42s?  Why or why not?

**Nope.  Now all gather on low side (4pts)**

iv. 8pts Write a single expression using the variables in the loop that determines whether the low side is smaller than the high side at any point during the partition (not just the end).  Remeber that the range being partitioned is [lo, hi).

```
if (                                 ) // If low is smaller
```

**endOfLow - lo < toPlace - endOfLow 8pts,  <= 7pts, minor oboe 5pts**

d. 8pts Values equal to pivot can actually be used to balance the partition if you place them in whichever side (low or hi) is smallest.  Without adding any new lines, do this by adding a single relatively sophisticated additional logic clause to the `if (vals[toPlace < pivot])` line.  

**|| (4pts) vals[toPlace] == pivot (2pts) && endOfLow - lo < toPlace - endOfLow (4pts)**

```
   endOfLow = lo;       
   pivot = vals[hi-1];     

   for (toPlace = lo; toPlace < hi-1; toPlace++)
      if (vals[toPlace] < pivot) {       
         temp = vals[toPlace];
         vals[toPlace] = vals[endOfLow];
         vals[endOfLow] = temp;
         endOfLow++;
      }                         

   vals[hi-1] = vals[endOfLow];  // Move pivot to middle
   vals[endOfLow] = pivot;
   
   return endOfLow;         
```

## 5. 32pts Priority queues and merge concepts
Below is an implementation of a merge algorith for LinkPQueue.  This merges
the receiver object and the q2 parameter into a single queue (with the sorting 
order determined by `cmp`).  It doesn't copy data; it actually steals the nodes
from q2 and adds them into the receiver object, leaving q2 empty.

Fill in the blank lines so it works correctly.  Do not add code other than for
the blank lines.
```
// Merge q2's nodes into the receiver object, leaving q2 empty. Write a
// loop to directly merge the nodes, don't use recursion, an iterator
// or any other methods of the class.
public void merge(LinkPQueue<Item> q2) {
   Node prior = null, nd1 = mHead, nd2 = q2.mHead, nextMerge;

   while (nd2 != null) { 
      if (nd1 == null) {       // Rest of q2 adds to end of main queue
         nextMerge = nd2;
         __________________;       **mTail = q2.mTail; 12pts**
         nd2 = null;            
                                                            
      } else if (cmp.compare((Item)nd1.data, (Item)nd2.data) > 0) { 
         nextMerge = nd1;
         nd1 = nd1.next;
      } else {
         nextMerge = nd2;
         nd2 = nd2.next;
         
         _____________________________;  **nextMerge.next = nd1; 12pts**
      }
      if (prior == null)
         mHead = nextMerge;
      else
         prior.next = nextMerge;
      
      ___________________________;       **prior = nextMerge; 8pts**
   }
   q2.mHead = q2.mTail = null;
}
```

## 6. 42pts Complexity
### a. 8pts 
The selection sort algorithm has what order of complexity?  Why?

**O(n^2) 3pts, because an inner scan loop runs each time the outer loop runs 5pts**

### b. 20pts
If an implementation of insertion sort takes 0.1 second on average, sorting an array of 10,000 elements, how long will it take on an array of 640,000 elements?  Provide an estimate accurate to the tenth of a second. Note this can be done *in your head* if you remember powers of 2 and the nature of O(n^2) complexity.

**409.6 seconds 20pts**

### c. 14pts
If a balanced binary tree of 1,000 elements can be searched in 5 microseconds, how long would you expect a search to take on a balanced tree of 1,000,000 elements?  Why? Answer the same question if both trees are worst-case unbalanced.  As before, this can be done without a lot of computation.

**10 microseconds (7pts) because log doubles with square (3pts).  For degenerate: 5,000 uS or 5 mS (4pts)** 



## 7. 64pts Java semantics 
### a. 8pts. 
How many distinct objects (not just references) are created by this line of code
creating a 5-row 2-D array? How many of them are String objects? `String[][] arr = new String[5][3]` 

**6 6pts (21 3pts).  None are Strings 2pts**

### b. 8pts 
How many *references* are created by that same line? How many, if any, are null?  

**1 + 5 + 15 = 21 5pts, of which 15 are null 3pts**

### c. 8pts
Write a declaraion of a member function "func" that has *absolutely* no parameters passed, hidden or otherwise.

**static void func() 8pts**

### d. 8pts
Why is the following line illegal?  (Assume Comparator is correctly imported) `Comparator cmp = new Comparator();` (Note that use of the <> is optional in generics.) 

**Comparator is an interface, so no objects of it can be created. 8pts**

### e. 10pts 
Say function A calls B, which calls C, which calls D, and then D throws an exception that is caught in A.  What changes, if any, to the RTS must result from throwing that exception? 

**Frames for D, C, and B are popped 10pts**

### f. 18pts
Assume this loop compiles.  Write an equivalent using just a standard while
loop, and proper assumptions about vals.  Don't worry about imports.  (Note that vals need not be an array, nor indexable, for this loop to work.)

```
for (String s: vals)
   # Loop body

```
**Answer:**
```
Iterator<String> itr = vals.iterator(); **8pts (no <String> 6pts)**
while (itr.hasNext()) {                 **4pts**
   String s = itr.next();               **6pts**
   # Loop body
}
```

## 8. 42pts LPHashSet
Fill in the blanks, and fix any bugs, in this implementation of LPHashSet.check.
Don't fix things that aren't broken.  You don't need to check "steps", just the
main logic.
```
   private boolean check() {
      int vCount = 0, nCount = 0, homeIdx;
      
      for (int idx = 0; idx < table.length; idx++) {
         if (table[idx] == null)
            nCount++;
                           **table[idx] != wasFull 10pts != null 4pts**
         else if (________________________) { 
            vCount++;
            homeIdx = Math.abs(table[idx].hashCode()) % table.length;
            while (______________________) { **homeIdx != idx 8pts**
               if (table[homeIdx] == null)
                  ____________________________ **return false; 8pts**
               homeIdx = (homeIdx + 1) % table.length; **+ -> - 12pts**
            }
         }
      }
      
      return vCount == valCount || nCount == nullCount;
      **|| => && 5pts**
   }
```

## 9. 27pts DFS Project
Fill in the missing line in this implementation of getDistance for GemState

```
 public int getDistance(State st) {
   GemState gState = _________________________   **(GemState)st 4pts**
   int ndx, dist = 0, rDiff, cDiff;
   
   for (ndx = 0; ndx < gState.mBoard.length; ndx++) {
      **&& 2pts gState.mBoard[ndx]-1 != ndx 10pts (oboe 5pts)**
      if (gState.mBoard[ndx] != 0 _____________________________________) {  
         rDiff = (gState.mBoard[ndx]-1) / mColDim - ndx / mColDim;
               **(gState.mBoard[ndx]-1) % mColDim - ndx % mColDim; 
               1pt for each term; 5pts for each mod operator**
         cDiff = ___________________________________________________
         dist += (rDiff > 0 ? rDiff : -rDiff) + (cDiff > 0 ? cDiff : -cDiff);
      }
   }
         
   return dist;
}
```

