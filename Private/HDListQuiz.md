# Link Queue Quiz

Name_________________________________________________

Below is an implementation of a variation of `LinkQueue` that uses a dummy node, a bit like the one for `DblLinkQueue`.  This dummy node is always present (created in the constructor, as you can see), and is always pointed to by `mHead` (and also by `mTail` when the queue is empty).  The dummy node contains no useful data; all the actual data is in the nodes after the dummy.  The real head of the queue is thus the node pointed to by the dummy node.

The code for the original `LinkQueue` is simplified by the addition of this dummy node, since `mHead` and `mTail` never become null, and there is always at least one node in the queue.  Below is an implementatin of this changed queue, with a number of blank lines to fill in.  Make no other changes, and don't cram multiple statements onto one line.  When there are only a few blank lines, you must complete the code using only those lines.

```
import java.util.Iterator;
import com.cstaley.csci182.Queues.Queue;

public class LinkHdQueue<Item> implements Queue<Item>, Iterable<Item> {     
   protected static class Node {
      public Object data;
      public Node next;
      
      public Node(Object d, Node n) {
         data = d;
         next = n;
      }
   }
   
   protected Node mHead;
   protected Node mTail;
   
   public LinkHdQueue() {
      mHead = mTail = new Node(null, null);   
   }
   
   // Make this queue a deep copy of "other". Do this the easy if slightly slow
   // way by using "add" and an iterator. 
   public LinkHdQueue(LinkHdQueue other) {

      _____________________________;  
      for (Object obj: other)
         add(obj);
   }
   
   @Override
   public void add(Object val) {

      ___________________________;  

      ___________________________;  
   }
   
   @Override
   public Item getFront() {
      if (isEmpty())
         throw new NullPointerException();
         
      return (Item) ________________________;  
   }
   
   @Override
   public void remove() {
      if (isEmpty())
         throw new NullPointerException();

      _____________________________;   

      if (________________________)   
         mTail = mHead;
   }
   
   public boolean isEmpty() {

      return _______________________;  
   }
}
``` 