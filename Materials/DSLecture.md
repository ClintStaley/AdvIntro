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
      * missing final ||lineNdx < line.length()
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
#### Stacks1
   * Main concept of a stack
   * walk through push, in particular
   * receiver object
   * variadic push as side-demo
   * Questions (fill in final line of isOn)
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

#### Stacks3
  * Container classes and generics
  * Just make it a Stack of Objects??
     * What does that look like?  Try Strings.
     * Need to downcast top return, and nothing prevents pushing other types.
  * Generic notation
     * Does not change underlying container, which is of Objects.
     * Enforces in/out type rules via the reference itself.
  * 

