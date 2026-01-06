package  com.cstaley.stm3911.ProgTests.Answer;

public class LinkStack<Type> {

   protected static class Node {
      public Object data;
      public Node next;
   };

   protected Node head;
   
   public LinkStack() {}               // Defaults suffice
   
   public LinkStack(LinkStack<Type> stk) {
      head = stk.head;                 // Share nodes, at least to start with.
   }
   
   public void push(Type val) {
      Node temp = new Node();
      
      temp.data = val;
      temp.next = head;
      head = temp;
   }

   public void push(Type... vals) {
      for (Type val : vals)
         push(val);
   }
      
   public Type top() {
      if (head == null)
      throw new NullPointerException("Stack underflow");
      return (Type)head.data;
   }
   
   public void pop() {
      if (head == null)
         throw new NullPointerException("Stack underflow");
      head = head.next;
   }
   
   public boolean isEmpty() {return head == null;}

   // Swap top two nodes.  Do not use push, pop, top, or isEmpty.  Do not
   // swap the data, just the nodes. Throw NullPointerException if the stack
   // has fewer than two nodes.
   public void swapTopTwo() {
      Node temp;
      
      if (head == null || head.next == null)
         throw new NullPointerException("Stack underflow");

      temp = head.next;
      head.next = temp.next;
      temp.next = head;
      head = temp;
   }

   // Add stack "stk" to the top of this stack, leaving "stk" empty.  Do not
   // call any other stack methods to do this.
   public void concat(LinkStack<Type> stk) {
      Node temp;
      
      if (stk.head == null)
         return;
      
      for (temp = stk.head; temp.next != null; temp = temp.next)
         ;
      temp.next = head;
      head = stk.head;
      stk.head = null;
   }

   // Remove the nth element from the top of the stack, where n=0 is the top
   // element, n=1 is the next element, etc.  Throw IllegalArgumentException
   // if n is negative or too large. Use at most 14 lines, not counting blank
   // lines or lines with just a closing brace.
   public void remove(int n) {
      Node temp;
      
      if (n < 0 || head == null)
         throw new IllegalArgumentException("Negative index");

      if (n == 0)
         head = head.next;
      else {
         for (temp = head; temp.next != null && n > 1; temp = temp.next, n--)
            ;
         if (n == 1 && temp.next != null)
            temp.next = temp.next.next;
         else
            throw new IllegalArgumentException("Index too large");
      }
   }

   // Return a string representation of the stack, with the top element
   // on the left.  Separate elements with a comma and a space, and enclose
   // the list in square brackets.  Use the toString method of the elements
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("[");
      Node temp;
      
      for (temp = head; temp != null; temp = temp.next) {
         sb.append(temp.data);
         if (temp.next != null)
            sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
   }
   
   /* Expected output:
    * stk1: [6, 5, 7, 8, 1, 2, 3, 4] stk2: [] stk3: []
    * Caught java.lang.IllegalArgumentException: Index too large
    * Caught java.lang.IllegalArgumentException: Negative index
    * Caught java.lang.NullPointerException: Stack underflow
    * stk1: [5, 7, 8, 1, 3] stk2: [] stk3: [42]
    * stk1: [] stk2: [] stk3: [42]
    */
   public static void main(String[] args) {
      LinkStack<Integer> stk1 = new LinkStack<Integer>();
      LinkStack<Integer> stk2 = new LinkStack<Integer>();
      LinkStack<Integer> stk3 = new LinkStack<Integer>();
      
      stk1.push(4, 3, 2, 1);  // Note this puts 1 at top of stack
      stk2.push(8, 7, 6, 5);

      stk2.swapTopTwo();
      stk1.concat(stk3);
      stk3.concat(stk2);
      stk1.concat(stk3);
      
      System.out.printf("stk1: %s  stk2: %s  stk3: %s\n", stk1, stk2, stk3);
      
      stk1.remove(7);
      stk1.remove(0);
      stk1.remove(4);
      try {
         stk1.remove(6);
      } catch (IllegalArgumentException e) {
         System.out.println("Caught " + e);
      }
      try {
         stk1.remove(-1);
      } catch (IllegalArgumentException e) {
         System.out.println("Caught " + e);
      }
      try {
         stk3.push(42);
         stk3.swapTopTwo();
      } catch (NullPointerException e) {
         System.out.println("Caught " + e);
      }

      System.out.printf("stk1: %s  stk2: %s  stk3: %s\n", stk1, stk2, stk3);
      while (!stk1.isEmpty())
      stk1.remove(0);
      System.out.printf("stk1: %s  stk2: %s  stk3: %s\n", stk1, stk2, stk3);
   }
}