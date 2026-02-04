# Stacks Project

Starting with the Stacks3 course examples

### Bronze Level

Implement toString, for both LinkStack and ArrStack, producing a String of form [ val1, val2, val3 ] with top of stack at the *right*.  Do not do any String concatenation for this (no use of + on Strings).  Use StringBuilder.  Test your method by printing out, via System.out.println(stk) calls, both empty and filled ArrStacks and LinkStacks.

#### Submitting Bronze
Submit LinkStack.java, ArrStack.java, with test mains that exercise the printing of the stacks as specified.  Submit a snapshot of your test mains (both of them) running correctly.

### Silver Level

In text, certain pairs of letters are expected to balance one another, e.g. (), [], {}, or even "" (where the opening and closing character are the same).  We consider a string to be properly formed if all such characters pairs are properly closed and nested.  For instance, given the pairs above, these are balanced strings:

```
{a(bc[d]"e")}f
[{(a)}b]
```
But these are not, either due to missing openers/closers or due to improper nesting
```
"xx>
([{a]})
{{test}xxx
```
You can readily determine if a string is properly formed by maintaining a stack of opening symbols thus far seen, and matching closing symbols by ensuring the top of stack is the opening "partner" and popping off the opener if so.  Nesting requires a LIFO ordering, so the stack is the right data structure to organize this.  For instance, analysis of the string `[{(a)}b]` would push [, {, and ( as they're seen, and then pop each back off as their partner ), }, and ] symobls occur (in reverse order for proper nesting).  Non-paired characters like a and b are simply ignored.

Using this approach, write a Java class Balance.java which has a function isBalanced:

```
boolean isBalanced(String text, char[][] pairs)
```
where `pairs` is an nx2 array of characters, with each row representing a balanced pair, e.g. `[['(',')'], ['{','}'], ... ]`.  Return true iff `text` is properly balanced for the character-pairs in `pairs`.  Be sure this works even if test is 0 length.

Balance.java has a main program that reads in a single line comprising pairs, followed by as many lines as desired, each giving a string to check for balance.  It sets up the needed 2D array of chars, and calls isBalanced, outputting each of the test lines followed by "balanced" or "imbalanced".  Here's an example:

```
Input:

(){}[]""''**
(Hello[{**}])
*"test*"
(([a]b)c))

Output:
(Hello[{**}]) balanced
*"test*" imbalanced
(([a]b)c))``` imbalanced
```

#### Submitting Silver
Create a pair of test files, each with different balanced-pair sets, and with a set of test strings that include balanced and imbalanced strings, that make use of every character pair at some point, and that include missing openers, missing closers, extra final closers, and balanced but improperly nested strings.  Submit five files:

```
Balance.java
Test1.in
Test1.out
Test2.in
Test2.out
```