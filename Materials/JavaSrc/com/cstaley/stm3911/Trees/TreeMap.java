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
      Node parent = null, toDelete = root, toAdopt, toTrade;
      boolean goLeft = false;
      Object oldVal;
      
      // Walk parent/child down the tree till child matches toAdd or falls off.
      while (toDelete != null && cmp.compare(key, toDelete.key) != 0) {
         parent = toDelete;
         goLeft = cmp.compare(key, toDelete.key) < 0;
         toDelete = goLeft ? toDelete.left : toDelete.right;
      }

      if (toDelete == null)
         return null;
      else if (toDelete.left == null || toDelete.right == null) {
         toAdopt = toDelete.left != null ? toDelete.left : toDelete.right;
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
         
         toDelete.key = toTrade.key;
         oldVal = toDelete.value;
         toDelete.value = toTrade.value;
         if (parent == toDelete)
            parent.left = toTrade.left;
         else
            parent.right = toTrade.left;
      }
      size--;
      return (V) oldVal;
   }

   // Stopped here.  Write rest and review
   
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
