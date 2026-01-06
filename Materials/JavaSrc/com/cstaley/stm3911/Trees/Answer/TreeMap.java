package com.cstaley.stm3911.Trees.Answer;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cstaley.stm3911.Trees.TreeSet.Node;

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
   
   private class KeySet implements Set<K> {

      @Override public int size() {return size; }

      @Override public boolean isEmpty() {return size == 0;}

      @Override public boolean contains(Object o) {return containsKey(o);}

      @Override
      public Iterator<K> iterator() {
         // TODO Auto-generated method stub
         return null;
      }

      @Override
      public Object[] toArray() {
         // TODO Auto-generated method stub
         return null;
      }

      @Override
      public <T> T[] toArray(T[] a) {
         // TODO Auto-generated method stub
         return null;
      }

      @Override
      public boolean add(K e) {throw new UnsupportedOperationException();}

      @Override
      public boolean remove(Object o) {throw new UnsupportedOperationException();}

      @Override
      public boolean containsAll(Collection<?> c) {
         return false;
      }

      @Override
      public boolean addAll(Collection<? extends K> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void clear() {throw new UnsupportedOperationException();}
   }

   private Node root;
   private int size;
   private Comparator cmp;
   
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
   
   public TreeMap(Comparator cmp) {
      this.cmp = cmp;
   }

   @Override
   public boolean containsKey(Object key) {
      return find(key).nd != null;
   }
   
   @Override
   public int size() {return size;}
 
   @Override
   public boolean isEmpty() {return size == 0;}

   private static boolean containsValueRec(Node subRoot, Object val) {
      return subRoot != null && (subRoot.value.equals(val)
       || containsValueRec(subRoot.left, val)
       || containsValueRec(subRoot.right, val));
   }
   
   @Override
   public boolean containsValue(Object value) {
      return containsValueRec(root, value);
   }

   @Override
   public V get(Object key) {
      SearchRes res = find(key);
      
      return res.nd == null ? null : (V)res.nd.value;
   }

   @Override
   public V put(K key, V value) {
      SearchRes res = find(key);
      Object rtn;
      Node toAdd;
      
      if (res.nd != null) {     // Replace existing value
         rtn = res.nd.value;
         res.nd.value = value;
      }
      else {                    // Andd new key/value pair
         toAdd = new Node(key, value);
         size++;
         if (res.parent == null)
            root = toAdd;
         else if (res.leftChild) {
            res.parent.left = toAdd;
         }
         else {
            res.parent.right = toAdd;
         }
         rtn = null;
      }
      
      return (V) rtn;
   }

   @Override
   public V remove(Object key) {
      SearchRes res = find(key);
      Object rtn;
      Node toAdopt, toTrade, subParent;
      
      if (res.nd != null)
         return null;
      
      rtn = res.nd.value;
      if (res.nd.left == null || res.nd.right == null) {
         toAdopt = res.nd.left != null ? res.nd.left : res.nd.right;
         if (res.parent == null)
            root = toAdopt;
         else if (res.leftChild)
            res.parent.left = toAdopt;
         else
            res.parent.right = toAdopt;
      }
      else {
         subParent = res.nd;
         toTrade = res.nd.left;
         while (toTrade.right != null) {
            subParent = toTrade;
            toTrade = toTrade.right;
         }
         
         res.nd.key = toTrade.key;
         res.nd.value = toTrade.value;
         if (subParent == res.nd)
            subParent.left = toTrade.left;
         else
            subParent.right = toTrade.left;
      }
      size--;
      return (V) rtn;
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
