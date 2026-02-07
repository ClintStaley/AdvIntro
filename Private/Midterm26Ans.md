# Data Structures Midterm

Name_________________________________________________

## 1 48pts Vocabulary

Give terms to match each of these definitions.  Correct spelling is required.

Pseudo machine language into which Java source is translated. **bytecode**

Program that runs that pseudo machine code **virtual machine (JVM) or interpreter**

The class from which yours is derived (2 terms) **superclass, base class**

Constructor that requires no arguments **default constructor**

Methods that every class has (give two examples) **equals, toString, clone, wait, getClass**

Number of elements in a 4x8x32x16 4-D array **16384**

Cube root of 32768 **32**

Class that is designed to hold an elemental type in order to make it an object **wrapper class**

Type of comparison meant to be implemented via a call of `equals` **deep comparison**

Memory area from which `new` gets its storage.  **runtime heap**

Object referred to by `this` which is is implicitly passed as a parameter **receiver object**

Alternate term for "derived class" **subclass**

Program that translates from a language like Java to a machine language **compiler**

Program that directly reads and executes source code, like Python  **interpreter**

Four letter abbreviation for the order of adding and removing for a queue.  **FIFO**

*Three* different terms for the property that ensures that regardless of whether the ```stk``` pointer below points to a LinkStack, ArrStack, or any other kind of Stack, the correct ```push``` function will get called. **dynamic binding, runtime binding, polymorphism**

```
Stack stk = // Code that creates some kind of Stack object
stk.push(42)
```
Type relationship between Stack and LinkStack that permits this assignment: ```Stack stk = new LinkStack()``` **ISA**

Type of casting that is done in this assignment: ```Stack stk = new LinkStack()``` **upcasting**

Process by which an elemental type is automatically placed into a wrapper object when needed. **boxing**

Garbage collection phase in which unmarked objects are freed to reuse their storage **sweep**

## 2 42pts Java semantics 
a. 8pts What does the following code output?  Explain why.  How would you change it to make a deep comparison instead? **Prints Not equal (req)  Shallow comparison, reference comparison 4pts call s1.equals(s2) 4pts**

```
String s1 = "Test", s2 = "Test";

if (s1 == s2)
   System.out.println("Equal");
else
   System.out.println("Not equal");
```

b. 10pts What is the result of this line of code, and why?  (Assume import java.util.Iterator has been done.) **Error 2pts  Cannot create an Iterator 4pts  Iterator is just an interface 4pts**

```Iterator itr = new Iterator()```

c. 6pts
Here is a call of the stack isEmpty function.  How many parameters does it actually have, and why? **6pts Has "this" parameter**

boolean isEmpty() {
   return mHead == null;
}

d. 18pts Fill in just the blank lines to create a version of CountingLinkStack that refuses to add any duplicate values to the stack, silently ignoring push calls that would add a duplicate:

```
class NoDupsLinkStack **4pts extends CountingLinkStack** {
   public NoDupsLinkStack() {

      **4pts super()**
   }

   public void **2pts push(int val) {

      if **4pts (!IsOn(val))**

         **4pts super.push(val)**
   }

}
```

## 3. 40pts Java Coding: Doubly Linked Lists 
The following new method for DblLinkQueue swaps the first and second nodes (the two to the "right" of the dummy node in our diagrams) so that they trade positions in the queue.  It throws an exception if there are fewer than two nodes aside from the dummy.  Fill in the missing parts so it works as specified.  Note that `second` points to the second node to the right of the dummy.  Make no other changes.

```
public void swapFirstTwo() {
   // Point to second node in queue
   Node second = ____________________;   **5pts mHead.next.next**  
   
   ** || 4pts second == mHead 4pts  or just .prev 8pts**
   if (mHead.next == mHead ________________________)  // At least two nodes...
      throw new NullPointerException();
   
   second.next.prev = __________________________;    **8pts mHead.next**
   second.prev = mHead;
   mHead.next.prev = _________________;              **8pts second**
   
   ___________________________________   ** 7pts mHead.next.next = 7pts second.next;**
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
   
   if (mHead == ________)  mTail (5pts)

      ________________________; mHead (4pts) = mTail (2pt) = null (2pt);
   else {
      for (temp = mHead; temp[.next] (8pts) != mTail; temp = temp.next)
         ;
      mTail = temp;
      
      ___________________   mtail.next = null or temp.next = null;   6pts

      ___________________   size--; 4pts
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
         if (pair[0] == c) {           **[1] 4pts**
            return true;
         }
      }
      
      _________________;               **return false; 3pts **
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
         else {                   **&& => !! 5pts stack.isEmpty() 5pts**
            if (isCloser(c, pairs) || (___________________ || stack.pop() != c)) 
               
               ____________________  **return false 6pts**
         }
      }
      return ______________________    **stack.isEmpty() 10pts**
   }
```
