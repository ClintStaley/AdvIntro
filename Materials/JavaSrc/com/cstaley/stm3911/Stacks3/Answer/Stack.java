// Stack interface
package com.cstaley.stm3911.Stacks3.Answer;

public interface Stack<Type> {
   public abstract void push(Type val);
   public abstract Type top();
   public abstract Type pop();
   public abstract boolean isOn(Type val);
   public abstract Type get(int idx);
   public abstract int getSize();
   public abstract boolean isEmpty();
}