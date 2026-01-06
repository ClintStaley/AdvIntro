package com.cstaley.stm3911.Trees.Answer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.cstaley.stm3911.HashTables.Answer.LLHashSet;
import com.cstaley.stm3911.Stacks3.Answer.LinkStack;

public class TreeSetTest<E> implements Set<E> {

   private static class Node {
      Object data;
      Node left;
      Node right;

      Node(Object d) {
         data = d;
      }
   }

   private Node root;
   private Comparator cmp;
   private int size;

   public TreeSetTest(Comparator cmp) {
      this.cmp = cmp;
   }

   @Override
   public boolean add(E toAdd) {
      Node parent = null, child = root;

      while (child != null && !child.data.equals(toAdd)) {
         parent = child;
         child = cmp.compare(child.data, toAdd) < 0 ? child.right : child.left;
      }

      if (child != null)
         return false;
      else {
         size++;
         child = new Node(toAdd);
         if (parent == null)
            root = child;
         else if (cmp.compare(parent.data, toAdd) < 0)
            parent.right = child;
         else
            parent.left = child;
         return true;
      }
   }
   
   @Override
   public boolean contains(Object toFind) {
      Node nd = root;

      while (nd != null && !nd.data.equals(toFind))
         nd = cmp.compare(nd.data, toFind) < 0 ? nd.right : nd.left;

      return nd != null;
   }
   
   @Override
   public int size() {
      return size;
   }
   
   @Override
   public void clear() {
      root = null;
      size = 0;
   }

   @Override
   public boolean isEmpty() {
      return root != null;
   }

   @Override
   public boolean remove(Object toDrop) {
      Node parent = null, toAdopt, toTrade, toDelete = root;
      boolean goLeft = false;

      while (toDelete != null && cmp.compare(toDrop, toDelete.data) != 0) {
         parent = toDelete;
         goLeft = cmp.compare(toDelete.data, toDrop) > 0;
         toDelete = goLeft ? toDelete.left : toDelete.right;
      }

      if (toDelete != null) {
         if (toDelete.left == null || toDelete.right == null) {  // Adopt child
            toAdopt = toDelete.left == null ? toDelete.right : toDelete.left;
            if (parent == null)
               root = toAdopt;
            else if (goLeft)
               parent.left = toAdopt;
            else
               parent.right = toAdopt;
         }
         else {
            parent = toDelete;
            toTrade = toDelete.left; 
            while (toTrade.right != null) {
               parent = toTrade;
               toTrade = toTrade.right;
            }

            toDelete.data = toTrade.data;
            if (parent == toDelete)
               parent.left = toTrade.left;
            else
               parent.right = toTrade.left;
         }
         size--;
         return true;
      }
      
      return false;
   }

   @Override
   public boolean addAll(Collection<? extends E> toAdd) {
      boolean changed = false;
      
      for (E obj: toAdd)
         changed = changed | add(obj);   // | is like || but not short-circuited.
      
      return changed;
   }

   @Override
   public boolean containsAll(Collection<?> toCheck) {
      for (Object obj: toCheck)
         if (!contains(obj))
            return false;
      
      return true;
   }

   private class TSItr implements Iterator<E> {
      private LinkStack<Node> path;
      
      TSItr() {
         path = new LinkStack<Node>();
         for (Node nd = root; nd != null; nd = nd.left)
            path.push(nd);
      }

      @Override
      public boolean hasNext() {
         return !path.isEmpty();
      }

      @Override
      // Maintain on the stack only those nodes that have not yet been returned.
      // If a node's right branch is being explored, it's *not* on the stack anymore.
      public E next() {
         Node nd = path.top();
         Object rtn = nd.data;
         
         path.pop();
         if (nd.right != null) {
            for (nd = nd.right; nd != null; nd = nd.left)
               path.push(nd);
         }
         
         return (E) rtn;
      }
   }
   
   @Override
   public Iterator<E> iterator() {return new TSItr();}

   @Override
   public boolean removeAll(Collection<?> toRemove) {
      boolean changed = false;
      
      for (Object val: toRemove)
         changed = changed | remove(val);   // | is like || but not short-circuited.
      
      return changed;
   }

   @Override
   public boolean retainAll(Collection<?> toRetain) {
      List<E> toRemove = new LinkedList();
      
      for (E val: this)
         if (!toRetain.contains(this))
            toRemove.add(val);
      
      return removeAll(toRemove);
   }

   /* Homework answers */
   public boolean removeSmallest() {
      Node nd;
      
      if (root == null)
         return false;
      
      size--;
      if (root.left == null)
         root = root.left;
      else {
         for (nd = root; nd.left.left != null; nd = nd.left)
            ;
         nd.left = nd.left.right;
      }
      
      return true;
   }
   
   public void rotateLeft() {
      Node temp;
      
      if (root != null && root.right != null) {
         temp = root.right.left;        // Will be moving this across
         root.right.left = root;
         root = root.right;
         root.left.right = temp;
      }
   }

   public int getDepth() {
      LinkStack<Node> path = new LinkStack<Node>();
      Node nd;
      int depth = 0;
      boolean wentLeft = false;
            
      for (nd = root; nd != null; nd = nd.left) {
         path.push(nd);
         depth++;
      }

      while (!path.isEmpty()) {
         nd = path.top();
         if (nd.left != null && !wentLeft)
            path.push(nd.left);
         else if (nd.right != null) {
            path.push(nd.right);
            wentLeft = false;
         }
         else {
            depth = Math.max(depth, path.getSize());            
            path.pop();  // Remove leaf
            while (!path.isEmpty() && path.top().right == nd) {
               nd = path.top();
               path.pop();
            }
            wentLeft = true;
         }
      }      
      return depth;
   }
   
   static private int fillArray(Object[] arr, int start, Node subRoot)
   {
      if (subRoot != null) {
         start = fillArray(arr, start, subRoot.left);
         arr[start++] = subRoot.data;
         start = fillArray(arr, start, subRoot.right);
      }
      return start;
   }

   @Override
   public Object[] toArray() {
      Object[] rtn = new Object[size];
      int filled;
      
      filled = fillArray(rtn, 0, root);
      assert filled == size;
      
      return rtn;
   }

   @Override
   public <T> T[] toArray(T[] toFill) {
      if (toFill.length < size)
         toFill = (T[]) new Object[size];
      
      fillArray(toFill, 0, root);        // Covariance
      if (toFill.length > size)
         toFill[size] = null;            // Per doc spec
      
      return toFill;
   }

   static private void addDiagram(StringBuilder bld, int level, Node subRoot) {
      final int indent = 3;
      
      if (subRoot != null) {
         addDiagram(bld, level + indent, subRoot.left);
         for (int i = 0; i < level; i++)
            bld.append(' ');
         bld.append(subRoot.data + "\n");
         addDiagram(bld, level + indent, subRoot.right);
      }
   }
   
   @Override
   public String toString() {
      StringBuilder rtn = new StringBuilder();
      
      addDiagram(rtn, 0, root);
      
      return rtn.toString();
   }
   
   public static void main(String[] args) {
      Comparator cmp = new Comparator() {
         @Override
         public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            return s1.compareTo(s2);
         }
      };

      TreeSetTest<String> set = new TreeSetTest<String>(cmp);
      String cmd, arg;
      Scanner in = new Scanner(System.in);
      
      while (in.hasNext()) {
         cmd = in.next();
         System.out.println(cmd);
         if (cmd.equals("add"))
            System.out.print(set.add(in.next()));
         else if (cmd.equals("contains"))
            System.out.print(set.contains(in.next()));
         else if (cmd.equals("size"))
            System.out.print(set.size());
         else if (cmd.equals("isEmpty"))
            System.out.print(set.isEmpty());
         else if (cmd.equals("toString"))
            System.out.print(set);
         else if (cmd.equals("remove"))
            System.out.print(set.remove(in.next()));
         else if (cmd.equals("toString"))
            System.out.print(set);
         else if (cmd.equals("clear"))
            set.clear();
         else if (cmd.equals("iterate")) {
            for (String s: set)
               System.out.printf("%s ", s);
         }
         else if (cmd.equals("removeSmallest"))
            set.removeSmallest();
         else if (cmd.equals("rotateLeft"))
            set.rotateLeft();
         else if (cmd.equals("containsAll")) {
            System.out.print(set.containsAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("addAll")) {
            System.out.print(set.addAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("retainAll")) {
            System.out.print(set.retainAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         else if (cmd.equals("removeAll")) {
            System.out.print(set.removeAll
             (Arrays.asList(in.nextLine().trim().split(" +"))));
         }
         System.out.println();
      }
   }
}

/* Test input
isEmpty
iterate
toString
add gamma
addAll beta alpha epsilon delta zeta omicron xi
toString
containsAll alpha epsilon
remove gamma
toString
removeSmallest
removeSmallest
toString
removeAll phi delta
toString
rotateLeft
toString
iterate
size
*/