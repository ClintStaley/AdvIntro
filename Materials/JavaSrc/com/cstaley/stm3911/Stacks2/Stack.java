// Stack interface
package com.cstaley.stm3911.Stacks2;

public interface Stack {
   void push(int val);  // Effectively public abstract
   int top();
   void pop();
   boolean isOn(int val);
   boolean isEmpty();

   // Can only rely on other methods in the interface, not the implementation
   default void push(int... vals) {
      for (int val: vals)
         push(val);
   }
}