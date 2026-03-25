package com.cstaley.stm3911.ProgTests.Answer;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

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
   private Comparator<E> cmp;
   private int size;

   public TreeSet(Comparator<E> cmp) {
      this.cmp = cmp;
   }

   @Override
   public boolean add(E toAdd) {
      Node parent = null, child = root;

      while (child != null && !child.data.equals(toAdd)) {
         parent = child;
         child = cmp.compare((E)child.data, toAdd) < 0 ? child.right : child.left;
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

   private class TSItr<E> implements Iterator<E> {
      protected LinkStack<Node> path;
      
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

   // Iterator that returns elements in the range [low, high).  Implement
   // solely by extending TSItr and overriding the constructor and hasNext method.
   public class RangeItr<E> extends TSItr<E> {
      public Object high;

      RangeItr(E low, E high) {
         path = new LinkStack<Node>();
         this.high = high;
         for (Node nd = root; nd != null;)
            if (low == null || cmp.compare(nd.data, low) >= 0) {
               path.push(nd);
               nd = nd.left;
            }
            else
               nd = nd.right;
      }
      
      @Override
      public boolean hasNext() {
         return super.hasNext() && 
            (high == null || cmp.compare(path.top().data, high) < 0);
      }
   }
   
   @Override
   public Iterator<E> iterator() {
      return new TSItr();
   }

   // Return iterator that starts at the first element >= low and ends when
   // next element > high.  If low is null, start at the first element.  If
   // high is null, end at the last element.
   public Iterator<E> rangeIterator(E low, E high) {
      return new RangeItr(low, high);
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
      Comparator<String> cmp = new Comparator<String>() {
         @Override
         public int compare(String o1, String o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            return s1.compareTo(s2);
         }
      };

      TreeSet<String> toTest = new TreeSet<String>(cmp);

      toTest.add("gamma");
      toTest.add("beta");
      toTest.add("alpha");
      toTest.add("delta");
      toTest.add("phi");
      toTest.add("pi");
      toTest.add("omega");
      toTest.add("omicron");
      toTest.add("sigma");
      toTest.add("theta");

      for (Iterator<String> itr = toTest.rangeIterator("gamma", "phi"); itr.hasNext();)
         System.out.println(itr.next());
      System.out.println();
      
      for (Iterator<String> itr = toTest.rangeIterator("beta", null); itr.hasNext();)
         System.out.println(itr.next());
      System.out.println();
      
      for (Iterator<String> itr = toTest.rangeIterator(null, null); itr.hasNext();)
         System.out.println(itr.next());
      System.out.println();
   }
}
