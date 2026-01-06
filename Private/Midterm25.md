# Adv Intro Midterm

Name_________________________________________________

## 1 48pts Vocabulary

Give terms to match each of these definitions.  Correct spelling is required.

Pseudo machine language into which Java source is translated. 

Program that runs that pseudo machine code (two different terms)  

Term for the class from which yours is directly derived 

Constructor that requires no arguments 

Methods that every Java class has (give two examples) 

2 to the 14th power

Class that is designed to hold an elemental type in order to make it an object 

Type of comparison meant to be implemented via a call of `equals` in Java

Memory area from which `new` gets its storage.

Object referred to by `this` or `self` 

Three-letter term for the idea that a derived class is type-compatible with a reference to its base class 

Alternate term for "derived class" 

Program that translates from a language like Java to a machine language

Program that directly reads and executes source code, like Python 

Four letter abbreviation for the order of adding and removing for a queue.  

Process by which a 1-dimension in a tensor is expanded to match a full dimension in another 

*Three* different terms for the property that ensures that regardless of whether the ```stk``` pointer below points to a LinkStack, ArrStack, or any other kind of Stack, the correct ```push``` function will get called. 

```
Stack stk = // Code that creates some kind of Stack object
stk.push(42)
```
Type relationship between Stack and LinkStack that permits this assignment: ```Stack stk = new LinkStack()``` 

Type of casting that is done in this assignment: ```Stack stk = new LinkStack()``` 

Process by which an elemental type is automatically placed into a wrapper object when needed. 

## 2 41pts Java semantics 
a. 12pts What does the following code output?  Explain why.  How would you change it to make a deep comparison instead? 

```
String s1 = "Test", s2 = "Test";

if (s1 == s2)
   System.out.println("Equal");
else
   System.out.println("Not equal");
```

b. 8pts What is the result of this line of code, and why?  (Assume import java.util.Iterator has been done.) 

```Iterator itr = new Iterator()```

c. 6pts
Here is a call of the stack isEmpty function.  How many parameters does it actually have, and why? 

boolean isEmpty() {
   return mHead == null;
}

d. 15pts Fill in just the blank lines to create a version of CountingLinkStack that refuses to add any duplicate values to the stack, silently ignoring push calls that would add a duplicate:

```
class NoDupsLinkStack _______________________ {
   public NoDupsLinkStack() {

      ____________________
   }

   public void ___________________ {

      if _________________

         _______________________
   }

}
```

## 3. 40pts Java Coding: Doubly Linked Lists 
The following new method for DblLinkQueue swaps the first and second nodes (the two to the "right" of the dummy node in our diagrams) so that they trade positions in the queue.  It throws an exception if there are fewer than two nodes aside from the dummy.  Fill in the missing parts so it works as specified.  Note that `second` points to the second node to the right of the dummy.  Make no other changes.

```
public void swapFirstTwo() {
   // Point to second node in queue
   Node second = ____________________;   
   
   if (mHead.next == mHead ________________________)  // At least two nodes...
      throw new NullPointerException();
   
   second.next.prev = __________________________;   
   second.prev = mHead;
   mHead.next.prev = _________________;            
   
   ___________________________________   
   second.next = mHead.next;
   mHead.next = second;
}
```
## 4 23pts Python Semantics
a. 15pts Fill in the missing code per instructions

```
nums = [10, 20, 30]

# Make deep copy by list comprehension
nums2 = ______________________________ 

nums2[-2] += 10
nums[-1] -= 10

# Set nums3 to max of nums and nums2, via a comprehension
nums3 = _______________________________ 
print(nums3)      # prints [10, 30, 30]
```

b. 8pts What is the result of the following print and why?

```
vals = [2, 5, 7]
vals2 = vals
vals.append(9)
print(vals2[-1])

```

## 5. 30pts Python Coding
Here is an implementation of the add_lifeform method from our Conway's Life project, basic version.
It has 5 bugs to fix, and 3 blank spots to fill in.  Fix the bugs and fill in 
the blanks.  Don't fix things that aren't broken.

```
   def add_lifeform(self, pattern_index, center_row, center_col):
        """Add a preconfigured life form centered at the specified location with 
        toroidal wrapping"""
            
        pattern = self.PATTERNS[pattern_index]  # Assume PATTERNS is correct
        pattern_height = len(pattern)
        pattern_width = ______________________
        
        # No 1x1 patterns allowed
        assert pattern_height > 1 and pattern_width > 1, \
            "1x1 patterns are not allowed"
        
        # Calculate top-left corner of pattern
        start_row = center_row - pattern_height 
        start_col = center_col - pattern_width 
        
        # Place pattern with toroidal wrapping
        for p_row in range(pattern_height):
            for p_col in range(pattern_width):
                # Calculate grid position with toroidal wrapping
                grid_row = _______________________________

                grid_col = _______________________________
                    
                # Set cell to alive and mark as changed
                self.grid[p_row][p_col] = pattern[p_row][p_col]
                self.changed_cells.append(grid_row, grid_col)
```

## 6.33pts Numpy Coding

a. 15pts Tensor `pts` represents an array of 8 points.  Show the output of each print.

```
import numpy as np
pts = np.array(
   [[1,2], [3,4], [2,5], [4,1], [3,2], [1,5], [2,2], [0,3]])
print(pts.shape)                    
a = np.mean(pts, axis=1)
print(a.shape)                      
print(a)                           

b = np.mean(pts, axis=0)
print(b.shape)                      
print(b)                           

```

b. 18pts The points in pts are clearly centered in the first quadrant.  Starting with just pts, write a single assignment that moves the entire cloud to center instead on the origin, putting this into zerodPts.  Your code must work for any set of point values, not just this one concrete example.

zerodPts = _____________________________________ 

Add an assigment that cuts the distance from each point to the origin in half, effectively "compressing" the cloud of points without changing its basic shape.

closerPts = ___________________________________  


