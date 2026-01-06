public class EchoName {
   // Input a name via a Scanner and repeat it back in a hello message
   public static void main(String[] args) {
      String name;  // User's name
      java.util.Scanner scn = new java.util.Scanner(System.in);

      System.out.print("Enter your name: ");
      name = scn.nextLine();

      System.out.println("Hello, " + name + "!");
   }
}
