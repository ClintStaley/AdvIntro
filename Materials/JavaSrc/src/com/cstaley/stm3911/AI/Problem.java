package com.cstaley.stm3911.AI;

import java.util.Iterator;
import java.util.Scanner;

public interface Problem {
   // One situation or configuration in a Problem solution.  This might be a 
   // location in a maze, or a layout of tiles in a Gem puzzle, or a Rubik's cube 
   // configuration.  The details vary per Problem, but any State must provide an 
   // iterator giving possible Steps leading from this State, and an applyStep
   // method that produces the new State that results from applying a Step to 
   // this State.
   public interface State extends Iterable<Step> {
      public Iterator<Step> iterator();  // All Steps leading from this State

      public State applyStep(Step step); // Generate new State from "step"
      public int getDistance(); // Best possible distance to a goal state
      public boolean isGoal();  // Whether this State is a goal state
   }
   
   // One step in a solution, e.g. a maze move, puzzle piece insertion, etc. that
   // causes a change from one State to the next.  The details vary depending on 
   // the Problem, but all Steps must have a cost.
   public interface Step {
      public int getCost();
   }

   // Read the Problem in
   public void read(Scanner in) throws Exception;
   
   // Get the starting State, for instance the starting location of a maze, or
   // the initial Gem puzzle layout
   public State getStartState();  
}