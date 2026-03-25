package com.cstaley.stm3911.Trees;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class TreeMap<K, V> implements Map<K, V> {
   private static class Node {
      Object key;
      Object value;
      Node left;
      Node right;

      Node(Object k, Object v) {
         key = k;
         value = v;
      }
   }

   private Node root;
   private int size;
   private Comparator cmp;
   
   public TreeMap(Comparator cmp) {
      this.cmp = cmp;
   }

   private class SearchRes {
      Node parent;
      Node nd;
      boolean leftChild;
      
      SearchRes() {nd = root;}
   }
   
   private SearchRes find(Object key) {
      SearchRes res = new SearchRes();
      
      while (res.nd != null && cmp.compare(key, res.nd.key) != 0) {
         res.parent = res.nd;
         res.leftChild = cmp.compare(key, res.nd.key) < 0;
         res.nd = res.leftChild ? res.nd.left : res.nd.right;
      }
      return res;
   }

   @Override
   public boolean containsKey(Object key) {return find(key).nd != null;}
   
   @Override
   public int size() {return size;}

   @Override
   public boolean isEmpty() {return size == 0;}

   @Override
   public boolean containsValue(Object value) {
      // Recurse on the tree
   }

   @Override
   public V get(Object key) {
      SearchRes res = find(key);
      
      return res.nd != null ? (V) res.nd.value : null;
   }

   @Override
   public V put(K key, V value) {
      Node parent = null, child = root;   
      
      // Walk parent/child down the tree till child matches toAdd or falls off.
      while (child != null && cmp.compare(key, child.key) != 0) {
         parent = child;
         child = cmp.compare(key, child.key) > 0 ? child.right : child.left;
      }
      
      if (child != null) { // Found a match
         Object oldVal = child.value;
         child.value = value;
         return (V) oldVal;
      }
      else {
         if (parent == null)
            root = new Node(key, value);
         else if (cmp.compare(key, parent.key) > 0)  // Fell off the right
            parent.right = new Node(key, value);
         else 
            parent.left = new Node(key, value);         
         size++;
         return null;
      }
   }

   @Override
   public V remove(Object key) {
      Node parent = null, child = root;
      int res;

      // Walk parent/child down the tree till child matches toDrop or falls off.
      // If we fall off, return false.
      while (child != null 
         && (res = cmp.compare((E)key, (E)child.key)) != 0) {
         parent = child;
         child = res < 0 ? child.left : child.right;
      }

      if (child == null)
         return null;

      // If child has two children, replace it with the min node in its
      // right subtree, and then remove that min node instead. This
      // leaves us with a node that has at most one child.
      if (child.left != null && child.right != null) {
         Node minParent = child, min = child.right;

         while (min.left != null) {     
            minParent = min;
            min = min.left;
         }

         child.key = min.key;
         child.value = min.value;
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

      return (V) child.value;
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
      // TODO Auto-generated method stub

   }

   @Override
   public void clear() {
      // TODO Auto-generated method stub

   }

   @Override
   public Set<K> keySet() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Collection<V> values() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      // TODO Auto-generated method stub
      return null;
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
   }
}
