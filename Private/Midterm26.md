# Data Structures Midterm

Name_________________________________________________

## 1 48pts Vocabulary

Give terms to match each of these definitions.  Correct spelling is required.

Pseudo machine language into which Java source is translated. 

Program that runs that pseudo machine code 

The class from which yours is derived (2 terms) 

Constructor that requires no arguments 

Methods that every class has (give two examples) 

Number of elements in a 4x8x32x16 4-D array 

Cube root of 32768

Class that is designed to hold an elemental type in order to make it an object 

Type of comparison meant to be implemented via a call of `equals`

Memory area from which `new` gets its storage.  

Object referred to by `this` which is is implicitly passed as a parameter 

Alternate term for "derived class" 

Program that translates from a language like Java to a machine language 

Program that directly reads and executes source code, like Python  

Four letter abbreviation for the order of adding and removing for a queue.  

*Three* different terms for the property that ensures that regardless of whether the ```stk``` pointer below points to a LinkStack, ArrStack, or any other kind of Stack, the correct ```push``` function will get called. 

```
Stack stk = // Code that creates some kind of Stack object
stk.push(42)
```
Type relationship between Stack and LinkStack that permits this assignment: ```Stack stk = new LinkStack()``` 

Type of casting that is done in this assignment: ```Stack stk = new LinkStack()``` 

Process by which an elemental type is automatically placed into a wrapper object when needed.

Garbage collection phase in which unmarked objects are freed to reuse their storage 

## 2 42pts Java semantics 
**a. 8pts** What does the following code output?  Explain why.  How would you change it to make a deep comparison instead? 

```
String s1 = "Test", s2 = "Test";

if (s1 == s2)
   System.out.println("Equal");
else
   System.out.println("Not equal");
```

**b. 10pts** What is the result of this line of code, and why?  (Assume import java.util.Iterator has been done.) 

```Iterator itr = new Iterator()```

**c. 6pts**
Here is a call of the stack isEmpty function.  How many parameters does it actually have, and why? 

boolean isEmpty() {
   return mHead == null;
}

**d. 18pts** Fill in just the blank lines to create a version of CountingLinkStack that refuses to add any duplicate values to the stack, silently ignoring push calls that would add a duplicate:

```
class NoDupsLinkStack ___________________________________ {
   public NoDupsLinkStack() {

      
   }

   public void **2pts push(int val) {

      if (_______________________)**

         ________________________
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

## 4. 33pts Java Coding: LinkQueue
Fix this implementation of undoAdd for LinkQueue by adding the blank lines, and
repairing one other bug.  Do not change anything that's not broken.
```
public void undoAdd() {
   Node temp;
   
   if (mHead == null)
      throw new NullPointerException();
   
   if (mHead == ________) 

      ________________________; 
   else {
      for (temp = mHead; temp[.next] (8pts) != mTail; temp = temp.next)
         ;
      mTail = temp;
      
      ___________________   

      ___________________  
   }
}
```

## 5. 33pts Java Coding: isBalanced
Fix this implementation of the isBalanced function from Stacks Silver project,
by filling in the 4 blanks, and fixing 2 other bugs. Do not change anything 
that's not broken.
```
   // Return Character representing the closing match for c, or null if c has
   // no match.
   private static Character getMatch(char c, char[][] pairs) {
      for (char[] pair : pairs) {
         if (pair[0] == c) {
            return pair[1];
         }
      }
      return null;
   }

   // Return true if c is a "closing" character e.g. })].
   private static boolean isCloser(char c, char[][] pairs) {
      for (char[] pair : pairs) {
         if (pair[0] == c) {          
            return true;
         }
      }
      
      _________________;             
   }

   // Return true iff text is properly balanced for the character-pairs in 
   // pairs, an nx2 array with each row representing a balanced pair,  
   // e.g. [['(',')'], ['{','}'], ... ].
   static boolean isBalanced(String text, char[][] pairs) {
      LinkStack<Character> stack = new LinkStack<Character>();
      for (char c : text.toCharArray()) {
         Character partner = getMatch(c, pairs);
         if (partner != null) {
            stack.push(partner); 
         } 
         else {                  
            if (isCloser(c, pairs) || (___________________ || stack.pop() != c)) 
               
               ____________________  
         }
      }
      return ______________________   
   }
```
