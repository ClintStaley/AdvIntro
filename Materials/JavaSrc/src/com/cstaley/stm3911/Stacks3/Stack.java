// Stack interface
package com.cstaley.stm3911.Stacks3;

public interface Stack<Type> {
   void push(Type val);
   Type top();
   Type pop();
   boolean isOn(Type val);
   int getSize();
   boolean isEmpty();

   default void push(Type... vals) {
      for (Type val: vals)
         push(val);
   }
}