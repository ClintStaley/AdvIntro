package com.cstaley.stm3911.Sorting;

import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SortPeople {
   static class Person {
      String firstName;
      String lastName;
      double gpa;
   }
   
   public static void main(String args[]) {
      Comparator<Person> cmp = new Comparator<Person>() {
         public int compare(Person p1, Person p2) {
            return p1.lastName.equals(p2.lastName) ? 
             p1.firstName.compareTo(p2.firstName) : 
             p1.lastName.compareTo(p2.lastName);
         }
      };
      Set<Person> ppl = new TreeSet<Person>(cmp);
      Scanner in = new Scanner(System.in);
      Person next;
      
      while (in.hasNext()) {
         next = new Person();
         next.firstName = in.next();
         next.lastName = in.next();
         next.gpa = in.nextDouble();
         ppl.add(next);
      }
      
      for (Person prs: ppl) {
         System.out.printf("%20s, %10s %.3f\n", prs.lastName, prs.firstName,
          prs.gpa);
      }
   }
}

/* IHS Source Module
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
(<1:30:30: // One more import >)

public class SortPeople {
   static class Person {
      String firstName;
      String lastName;
      double gpa;
   }
   
   public static void main(String args[]) {
      (<6:74:200: // A single, long, variable decl and init>)
      Set<Person> ppl = (<1:30:25:>);
      Scanner in = new Scanner(System.in);
      Person next;
      
      while (in.hasNext()) {
         next = new Person();
         next.firstName = in.next();
         next.lastName = in.next();
         next.gpa = in.nextDouble();
         ppl.add(next);
      }
      
      for ((<1:25:15:>)) {
         System.out.printf("%20s, %10s %.3f\n", prs.lastName, prs.firstName,
          prs.gpa);
      }
   }
}
*/
