package com.cstaley.stm3911.MMind.V2.ans;

public class MMind {
   public static void main(String[] args) {
      try {
         new Session().run();
      } catch (Exception err) {
         System.out.println("Unrecoverable format error");
      }
   }
}