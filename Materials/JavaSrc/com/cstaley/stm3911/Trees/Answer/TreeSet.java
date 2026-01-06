package com.cstaley.stm3911.Trees.Answer;

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
   private Comparator cmp;
   private int size;

   public TreeSet(Comparator cmp) {
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
   public boolean addAll(Collection<? extends E> arg0) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean containsAll(Collection<?> arg0) {
      // TODO Auto-generated method stub
      return false;
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
      public E next() {
         Node nd = path.top();
         Object rtn = nd.data;
         
         if (nd.right != null) {
            for (nd = nd.right; nd != null; nd = nd.left)
               path.push(nd);
         }
         else {
            path.pop();  // Remove child
            while (!path.isEmpty() && path.top().right == nd) {
               nd = path.top();
               path.pop();
            }
         }
         
         return (E) rtn;
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

   /* Homework answers */
   public E findLargest() {
      Node nd = root;
      
      while (nd != null && nd.right != null)
         nd = nd.right;
      
      return (E) nd.data;
   }
   
   public void rotateRight() {
      Node temp;
      
      if (root != null && root.left != null) {
         temp = root.left.right;
         root.left.right = root;
         root = root.left;
         root.right.left = temp;
      }
   }

   private static int getDepthRec(Node subRoot) {
      return subRoot == null ? 0 
       : 1 + Math.max(getDepthRec(subRoot.left), getDepthRec(subRoot.right));
   }

   public int getDepth() {return getDepthRec(root);}

   private static boolean verifyRec(Node root, Comparator cmp, Object lo, Object hi) {
      return root == null && (lo == null || hi == null || cmp.compare(lo, hi) < 0) 
       || verifyRec(root.left, cmp, lo, root.data) 
       && verifyRec(root.right, cmp, root.data, hi);
   }
   
   public boolean verify() {return verifyRec(root, cmp, null, null);}
   
   /* Nonrecursive version
   public int getDepth() {
      LinkStack<Node> path = new LinkStack<Node>();
      Node nd;
      int depth = 0;
      boolean wentLeft = false;
            
      if (root != null)
         path.push(root);
      
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
   */
   
   public int printDiagram() {
      LinkStack<Node> path = new LinkStack<Node>();
      Node nd;
      int depth = 0;
      boolean wentLeft = false;
      
      if (root != null)
         path.push(root);
      
      while (!path.isEmpty()) {
         nd = path.top();
         if (nd.left != null && !wentLeft)
            path.push(nd.left);
         else {
            
            for (int i = 0; i < path.getSize(); i++)
               System.out.print("   ");
            System.out.println(nd.data);
            
            if (nd.right != null) {
               path.push(nd.right);
               wentLeft = false;
            }
            else {            
               path.pop();  // Remove leaf
               while (!path.isEmpty() && path.top().right == nd) {
                  nd = path.top();
                  path.pop();
               }
               wentLeft = true;
            }
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
   
   public void reBalance() {
      Node temp, parent = root;
      
      while (parent.right != null && parent.right.right != null) {
         temp = parent.right;
         parent.right = temp.right;
         temp.right = parent.right.left;
         parent.right.left = temp;
         parent = parent.right;
      }
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

      System.out.printf("Empty: depth %d\n", toTest.getDepth());
      System.out.printf("--%s--\nbeta: %b delta: %b alpha: %b\n", toTest,
       toTest.contains("beta"), toTest.contains("delta"), toTest.contains("alpha"));
      for (String s: toTest)
         System.out.println(s);

      toTest.add("beta");
      System.out.printf("\n\nOne node: depth %d\n", toTest.getDepth());

      toTest.add("alpha");
      for (String s: toTest)
         System.out.println(s);
      
      toTest.add("alpha");
      toTest.add("gamma");
      toTest.add("delta");
      toTest.add("pi");
      System.out.printf("\n\nFull: depth %d\n", toTest.getDepth());     
      for (String s: toTest)
         System.out.println(s);

      System.out.printf("%s\n%b %b %b\n\n", toTest, toTest.contains("beta"),
       toTest.contains("delta"), toTest.add("alpha"));
      
      toTest.printDiagram();
      toTest.remove("beta");
      toTest.printDiagram();
      System.out.printf("Verify: %b\n", toTest.verify());
      
      toTest.clear();
      toTest.add("alpha");
      toTest.add("beta");
      toTest.add("gamma");
      toTest.add("omega");
      toTest.add("omicron");
      toTest.add("phi");
      toTest.add("sigma");
      toTest.add("theta");
      System.out.println(toTest);
      toTest.reBalance();
      System.out.println(toTest);
   }
}
