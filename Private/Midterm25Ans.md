# Data Structures Midterm

Name_________________________________________________

### 1 44pts Vocabulary

Give terms to match each of these definitions.  Correct spelling is required.

Pseudo machine language into which Java source is translated. **bytecode**

Program that runs that pseudo machine code (two different terms)  **virtual machine (JVM) and interpreter**

The class from which yours is derived **superclass or base class**

Constructor that requires no arguments **default constructor**

Methods that every class has (give two examples) **equals, toString, clone, wait, getClass**

2 to the 14th power **16384**

Class that is designed to hold an elemental type in order to make it an object **wrapper class**

Type of comparison meant to be implemented via a call of `equals` **deep comparison**

Memory area from which `new` gets its storage.  **runtime heap**

Object referred to by `this` which is is implicitly passed as a parameter **receiver object**

Three-letter term for the idea that a derived class is type-compatible with a reference to its base class **ISA**

Alternate term for "derived class" **subclass**

Program that translates from a language like Java to a machine language **compiler**

Four letter abbreviation for the order of adding and removing for a queue.  **FIFO**

## 2 30pts Java semantics 
a. 12pts What does the following code output?  Explain why.  How would you change it to make a deep comparison instead? **Prints Not equal 1pt  Shallow comparison, reference comparison 7pts call s1.equals(s2) 4pts**

```
String s1 = "Test", s2 = "Test";

if (s1 == s2)
   System.out.println("Equal");
else
   System.out.println("Not equal");
```

## 3. 56pts Homework


d. 8pts
Here is a declaration for the LinkStack isEmpty function.  Explain why it actually has one parameter.

```
   boolean isEmpty() {
      return head != null;
   }
```
**8pts The receiver object is implicitly passed**


```
## 5. 22pts Coding: Array Queue
Below is a snippet from our `ArrQueue` class, showing relevant member data and
a skeletal getSize method that returns the number of items in the queue, using
the member data to determine this.  For instance, if mHead and
mTail are in the normal, not "wrapped around" configuration, then `mTail - mHead` is the queue size.
Fill in the two blank spots correctly and as concisely as possible.  Recall that 
the case of `mHead == mTail` can indicate either a full queue or an empty one, 
with the `mEmpty` boolean set to true in the empty case.
```
protected Object[] mData;
protected int mHead;
protected int mTail;
protected boolean mEmpty;
   
public int getSize() {

   ** 4pts mHead < mTail || 8pts mEmpty (4pts for excess mHead == mTail) **
   if (__________________________)                 // Not wrapped around the end
      return mTail - mHead;
   else                                            // Wrapped around the end
      return ___________________________________;
      ** 10pts mData.length - (mHead - mTail) OR mData.length - mHead + mTail**
}
```

## 6. 48pts Coding: Doubly Linked Lists 
The following new method for DblLinkQueue swaps the first and second nodes (the two to the "right" of the dummy node in our diagrams) so that they trade positions in the queue.  It throws an exception if there are fewer than two nodes aside from the dummy.  Fill in the missing parts so it works as specified.  Note that `second` points to the second node to the right of the dummy.  Make no other changes.

```
public void swapFirstTwo() {
   // Point to second node in queue
   Node second = ____________________;               **6pts mHead.next.next**  
   
   ** || 4pts second == mHead 4pts  or just .prev 8pts**
   if (mHead.next == mHead ________________________)  // At least two nodes...
      throw new NullPointerException();
   
   second.next.prev = __________________________;    **9pts mHead.next**
   second.prev = mHead;
   mHead.next.prev = _________________;              **9pts second**
   
   ___________________________________   ** 8pts mHead.next.next = 8pts second.next;**
   second.next = mHead.next;
   mHead.next = second;
}
```

Python Ideas

2 Numpy problems like the quiz



