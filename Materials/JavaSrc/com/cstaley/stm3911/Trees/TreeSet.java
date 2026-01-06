package com.cstaley.stm3911.Trees;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import com.cstaley.stm3911.Stacks3.Answer.LinkStack;

public class TreeSet<E> implements Set<E> {

   private static class Node {
      Object data;
      Node left;
      Node right;

      Node(Object d) {
         data = d;
      }
   }

   private Node root;
   private int size;
   private Comparator cmp;

   public TreeSet(Comparator cmp) {
      this.cmp = cmp;
   }

   @Override
   public boolean contains(Object toFind) {
      Node nd = root;
      int res;

      // Walk down the tree till we find toFind or fall off 
      // Use only one cmp.compare call per iteration
      while (nd != null && (res = cmp.compare(toFind, nd.data)) != 0) 
         nd = res < 0 ? nd.left : nd.right;
 
      // Return true if we found toFind, false otherwise
      return nd != null;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public boolean add(E toAdd) {
      Node parent = null, child = root;
      int res = 0;

      // Walk down the tree till we find toFind or fall off
      // Use only one cmp.compare call per iteration
      while (child != null && (res = cmp.compare(toAdd, child.data)) != 0) {
         parent = child;
         child = res < 0 ? child.left : child.right;
      }
      if (child != null)
         return false;
      else {
         Node newNode = new Node(toAdd);
         if (parent == null)
            root = newNode;
         else if (res < 0)
            parent.left = newNode;
         else
            parent.right = newNode;
         size++;
         return true;
      }
   }

   @Override
   public void clear() {
      root = null;
      size = 0;   
   }

   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   // Remove the first occurrence of "toDrop" from the tree. Return true if we
   // found and removed it, false otherwise.
   @Override
   public boolean remove(Object toDrop) {
      Node parent = null, child = root;
      int res;

      // Walk parent/child down the tree till child matches toDrop or falls off.
      // If we fall off, return false.
      while (child != null && (res = cmp.compare(toDrop, child.data)) != 0) {
         parent = child;
         child = res < 0 ? child.left : child.right;
      }

      if (child == null)
         return false;

      // If child has two children, replace it with the smallest node in its right
      // subtree, and remove that node from the right subtree.
      if (child.left != null && child.right != null) {
         Node minParent = child, min = child.right;

         while (min.left != null) {     
            minParent = min;
            min = min.left;
         }

         child.data = min.data;
         parent = minParent;
         child = min;
      }

      // If child has one or no children, remove it from the tree.
      Node toInherit = child.left == null ? child.right : child.left;

      if (parent == null)
         root = toInherit;
      else if (parent.left == child)
         parent.left = toInherit;
      else
         parent.right = toInherit;

      size--;
      return true;
   }

   @Override
   // Add all elements from "toAdd" to the tree. Return true if the tree changed
   public boolean addAll(Collection<? extends E> toAdd) {
      boolean changed = false;

      for (E e : toAdd)
         changed |= add(e);

      return changed;
      
   }

   @Override
   public boolean containsAll(Collection<?> toCheck) {
      for (Object obj : toCheck)
         if (!contains(obj))
            return false;

      return true;
   }

   private class TSItr implements Iterator<E> {
      private LinkStack<Node> path; // Path from root down to current node

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
      public E next() {
         Node nd = path.pop();    // Save stack top for later return

         for (Node next = nd.right; next != null; next = next.left)
            path.push(next);
         return (E) nd.data;
      }
   }

   @Override
   public Iterator<E> iterator() {
      return new TSItr();
   }

   @Override
   public boolean removeAll(Collection<?> arg0) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean retainAll(Collection<?> arg0) {
      // TODO Auto-generated method stub
      return false;
   }

   // Fill "arr", starting at index "start", with values from "subRoot", in order.
   // Return the next empty spot in the array after we've done the filling-in.
   static private int fillArray(Object[] arr, int start, Node subRoot) {
      return 0;
   }

   @Override
   public Object[] toArray() {
      // create and fill an array with values in this, without calling fillArray, dammit
      Object[] arr = new Object[size];
      int idx = 0; // Initialize idx to 0

      for (Object val: this)
         arr[idx++] = val;

      return arr;
   }

   @Override
   public <T> T[] toArray(T[] toFill) {

   }

   // Add an indented diagram of "subRoot" to "bld", with one line per node. Assume
   // the entire diagram is within a larger tree and is already to be indented by
   // "level" columns.
   static private void addDiagram(StringBuilder bld, Node subRoot, int level) {
      final int indent = 3;

      if (subRoot != null) {
         addDiagram(bld, subRoot.left, level + indent);
         for (int i = 0; i < level; i++)
            bld.append(" ");
         bld.append(subRoot.data + "\n");
         addDiagram(bld, subRoot.right, level + indent);
      }
   }

   @Override
   public String toString() {
      StringBuilder bld = new StringBuilder();
      addDiagram(bld, root, 0);

      return bld.toString();
   }

   // fill "arr" with the values from "subRoot" in order, starting at "baseIdx".
   private static int fillArray(Node subRoot, Object[] arr, int baseIdx) {
      
   }

   // Fill "arr" with the values from "root" in order.  This is a wrapper for the
   // recursive "fillArray" method above.
   private static void fillArray(Node root, Object[] arr) {

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

      TreeSet<String> toTest = new TreeSet<String>(cmp);

      toTest.add("beta");
      toTest.add("alpha");
      toTest.add("gamma");
      toTest.add("delta");
      toTest.add("pi");

      String[] arr = new String[toTest.size()];
      fillArray(toTest.root, arr);

      for (String s : toTest)
         System.out.println(s);

      System.out.printf("%s\n%b %b %b\n\n", toTest, toTest.contains("beta"),
            toTest.contains("delta"), toTest.add("alpha"));

      toTest.remove("beta");
      System.out.printf("%s\n%b\n", toTest, toTest.contains("beta"));
   }
}
   


