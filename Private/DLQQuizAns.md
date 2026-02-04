# DblLinkQueue Quiz

Name_________________________________________________

**1. 24pts**    
Fix this implementation of addToEnd for DblLinkQueue by filling in the blank lines, 
and repairing one other bug.
```
// Attach the nodes of src to the end of the receiver object, leaving src
// as a valid empty queue.
public void addToEnd(DblLinkQueue<Item> src) {
   mHead.prev.next = src.mHead.next;

   src.mHead_______________ = mHead.prev;   8pts .next.prev
   
   mHead.prev = src.mHead;                  8pts .prev;

   mHead.prev.next = _______;               4pts mHead
   
   src.mHead.next = src.mHead.prev = ___________;  4pts src.mHead
}
   
