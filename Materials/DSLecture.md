# DS Lecture

## Day 1
### Syllabus
 * Class interaction!
    * Pay attention
    * What you figure out, you remember
    * Tells me how people are doing as we go.
 * Course difficulty
    * 50% average, but adjusted to 70-100 scale
    * Perfect work, with feedback
    * Support from office hours
    * Rationale.  Competitive challenge and opportunity with AI
 * Completion of programs for eligibility
    * Necessary, not sufficient, condition
 * Working independently!
    * No AI for coding, except perhaps for EC
    * Will use in class to see how it works; will require in SE
 * Extra credit opportunities

## Overview of Content
### "Data Structures"
  * Data arrangements already important (objects, arrays..)
  * Much more sophisticated arrangements possible
  * Data -> algorithms and code, so also more advanced coding

### Java
  * What's it like to learn a second PL?
  * Language popularity, and keeping up with changes
  * Language design and history
      * Compilation vs interpretation
      * Bytecode, java interpreter, jvm
  * Java new ideas vis-a-vis Python:
    * Strong typing
    * Heavier class declaration and other syntax
    * Enormous library ecosystem (though Python's pretty good too)
    * Very good, regular, documentation
    * Designed for large projects
      * Big Code example: 
      * 100 LOC/sqft => 10,000 sqft or 1250 ft long printout
      * 40 MLoc => 10 miles of printout...
  * VSCode IDE

### MMind example
  * Run example and review game logic
  * "boilerplate" for main, statics, etc.
  * Guess.java
    * Python vs C-syntax (C-syntax is common)
      * curly brace enclosure
        * free form indentation, but style dictates
        * paren around conditionals
      * variable naming convention: camelCase
      * do-while loop
      * for loop
      * Comment notation
      * &&, ||, !
      * method chaining syntax
      * autoincrement
      * constructors have same name as class, and you use "new" to make objects
    * Semantic issues
      * strong typing
      * elemental types: ints, floats, chars, boolean
      * references to objects
      * initialization of data
      * Text data
         * String vs char
         * Char arithmetic, unicode/ASCII
         * Character class, and brief intro to static methods
      * System.out.printf, format specifiers
      * private data and need for accessors
      * static main method, testing
      * toString method and its use in printing
      * Exception handling w/ try/catch
    * Bugs: 
      * mispositioning of bad=false
      * missing final || lineNdx < line.length()
      * failure to update ch in while loop that skips whitespace in guess
  * Model.java
    * Concepts
      * Random class, seeds, pseudorandom numbers
      * character codes (ASCII/Unicode) and character arithmetic
      * arrays in Java (objects, length property, no array till allocated)
      * record type
      * nested classes
      * returning objects (references)
    * Bugs
      * otherUsed[idx] should be otherIdx on line32 
  * Game.java
    * Concepts
      * Use of nested class from outside requires scope
      * record members are private, and accessor is same-named
    * Bugs
      * whichTry logic results in too high a try-count
    * Questions
      * Why is play able to throw IOException??
      * Does guess point to the same object throughout? Does result?

  * Session.java
    * Concepts
      * constant declarations, reasons for this
      * while-true, break and continue
      * more elaborate format specifiers
      * shallow vs deep comparison, copy.
    * Bugs
      * Error check of combo possibilities LLM actually wrote this one.(Discuss powers, etc.)
      * Shallow string comparison for response.

   * Libraries and packages
      * packages and import
      * use of JavaDoc, drilling down to class
      * reading constructors, methods, etc.
      * String class example: immutability, concatenation
  * MMind.java
    * Most of logic in classes/objects.
    * Need one no-object starting point
    
### Review of OO Design
   * Class design basics
     * Grouping data and associated code, to help think about it as a unit
     * Design by choosing natural groupings:
       * Data, and functions
       * Example of Guess, Model, Game, Session
       * Methods for each, and data

### Stacks
   * Main concept of a stack, LIFO
   * Allows multiple implementations, not just arrays, as we'll see.
   * LIFO order useful in many contexts
   
#### Stacks1
   * walk through push, in particular
   * receiver object
   * Constructors
      * copy cons
      * method overloading
   * variadic push as side-demo
   * Questions 
      * fill in final line of isOn correctly
      * make copy constructor more efficient.

#### Stacks2
   * LinkStack implementation
      * Nested class
      * Nodes and linked lists
      * Basic ops push, top, pop, etc.
      * Copy constructor concept
      * Bugs: temp = stk.head.next in copy cons; test for temp != null in valAt
   * CountingArrStack and implementation inheritance
      * Add methods and data
      * Layer cake model of object.  
      * Constructor "super" call.
      * meaning of "protected" qualifier in base
      * ISA relationship
      * Inheritance terminology: super/sub class base/derived class, override, 
   * CountingLinkStack
      * New data example
      * Overriding methods
   * Stacks2 main
      * Declaration of stk1, stk2.
      * Change Stack to ArrStack or LinkStack in that declaration to illustrate ISA.
   * Interface
      * Abstract methods
      * Defines "stackness" -- ISA holds for all Stack implementations
      * superclass without implementation, only obligation.
   * Polymorphism concept
      * What "push" gets called on line 32?  How can it tell?
      * If I made up a third stack type and worked it into the main, what then?
      * Object "knows" what type it is, even if reference does not.
      * THE big idea in OO.  Polymorphism, dynamic "binding", runtime binding
      * (Alternative, which requires a known type of reference, is "static" binding
      or "compile time" binding)
      * Allows one to create code that addresses only the interface, but works
      with any implementation thereof, even ones not yet created.
   * Interface inheritance vs implementation inheritance.
     * CountingLinkStack from LinkStack is implementation inheritance -- extend
     a working base class with new/overridden methods and new data
     * LinkStack/ArrStack from Stack is interface inheritance -- define abstract
     base class and rely on polymorphism.  This is 95% use case because it
     permits *type flexibility*
   * Aside on default implementations e.g. of variadic push (added in Java 8)
     * Could that push method include, e.g., a reference to "data" array?
     * Must rely only on methods in the interface, and thus remains abstract in effect
     * Lets you add new methods to an interface without demanding implementation
     in all implementing classes, as long as a default generic implementation is possible.
   * Object ultimate base class
     * Can have more than one superinterface but only one superclass
     * Look up what it offers
     * What types of object can an Object reference refer to?
     * What about elementals?
   * Questions
      * Can LinkedList copy constructor be shallow?  What new methods would make
      this impossible?
      * Implement toString properly for both stacks
   * Bugs
      * CountingLinkStack copy cons lacks copy of numVals.

#### Stacks3
  * Container classes and generics
  * Just make it a Stack of Objects??
     * What does that look like?  Try Strings.
     * Need to downcast top return, and nothing prevents pushing other types.
  * Generic notation
     * Does not change underlying container, which is of Objects.
     * Enforces in/out type rules via the reference itself.

### Queues
  * General queue idea, FIFO

#### LinkQueue and LinkPQueue
  * Discuss how to add at end.  O(n) without a tail pointer
  * Review getFront and remove.  
     * Ignore modCount for now
  * Questions
    * Do we need a mTail update in remove?
    * Queue new functions in review session
  * Bugs: Missing mTail = temp in add
  * static constructor
    * Static method has no receiver object
    * variadic we've seen before.  This one creates a Queue
  * Iterator concept
    * Use of iterator directly in main
    * Design of iterator class
    * nonstatic inner classes
    * Iterator interface
    * Iterable interface and foreach loops (tie to Python)
    * Bugs: Order of assignments in next()
  * Can we modify the queue during iteration?
    * mod counting and error check
  * Priority queue
    * General idea
    * How to compare for priority: larger, smaller??
    * Comparator interface and compare rules
    * Subclass overriding add
    * Bugs: prior => prior.next (x2) in for-loop of add

#### DblLinkQueue and DblLinkPQueue
  * Discuss doubly linked list.  Advantages, etc.
  * Circularity and dummy head node
  * Add method
  * Question: can we use a while loop in isGood?
  * Bugs: Incorrect test in isEmpty

#### ArrQueue and ArrPQueue
  * Idea of queue in array, head, tail(disclusive)
  * Walkthrough of wraparound process
     * How to do wraparound logic?
  * Testing of full vs empty, need for empty flag
  * Resizing when full...
  
### Recursion
  * Functions calling themselves
  * Example of fct function
     * Imagine as independent calls
#### RTS
  * Tracking locals and parameters
  * Follow main -> f1 -> f2 example calls
  * Tie into recursion
#### Fib
  * Fibonacci numbers, numbered from 0, starting with 0 1 (1 2 3 5 8)
  * Follow call tree from Fib(5), noting repeats
  * Discuss RTS depth
  * Ask about Fib(100)
     * RTS depth
     * Run time
     * Could do by hand!
#### Memoizing
  * Static variable holding table, with -1s initially
  * Fill in on each call
  * Ask them to write the if-statement wrappers and pre-return save
#### Choose function
  * 
  
### Sorting and BinSearch

#### Binsearch
  * 