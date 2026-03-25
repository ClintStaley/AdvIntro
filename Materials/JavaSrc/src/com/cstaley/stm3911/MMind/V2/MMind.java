package com.cstaley.stm3911.MMind.V2;

public class MMind {
   public static void main(String[] args) {
      try {
         Session ssn = new Session();
         ssn.run();
      } catch (Exception err) {
         System.out.println("Unrecoverable format error");
      }
   }
}