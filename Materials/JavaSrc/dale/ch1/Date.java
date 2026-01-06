package dale.ch1;

public class Date {
   public static final int cMinYear = 1583;
   protected int year, month, day;

   public Date(int month, int day, int year) {
      this.month = month;
      this.day = day;
      this.year = year;
   }
   
   // Copy Constructor
   public Date(Date other) {
      year = other.year;
      month = other.month;
      day = other.day;
   }

   // Observers (or "getters")
   public int getYear() {
      return year;
   }

   public int getMonth() {
      return month;
   }

   public int getDay() {
      return day;
   }

   // Return this date as a String.
   @Override
   public String toString() {
      // Convert to using StringBuilder
      return (month + "/" + day + "/" + year);
   }

   // Return true iff other is the same date as this
   public boolean equals(Date other) {
      return year == other.year && month == other.month && day == other.day;
   }

   private static int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

   // Return true iff other is the day after this one.
   public boolean isNextDay(Date other) {
      return year == other.year && month == other.month && day == other.day - 1
       || year == other.year && month == other.month - 1 && day == daysInMonth[month - 1] && other.day == 1
       || year == other.year - 1 && month == 12 && day == 31 && other.month == 1 && other.day == 1;
   }
   
   // Example main for test purposes
   public static void main(String[] args) {
      Date d1 = new Date(1, 31, 2023);
      Date d2 = new Date(2, 1, 2023);
      Date d3 = new Date(d1);
      Date d4 = new Date(12, 31, 2023);
      Date d5 = new Date(1, 1, 2024);

      System.out.println(d1.equals(d3));
      System.out.println(d1 == d3);
      System.out.println(d4.isNextDay(d5));
      
      System.out.println(d1.getDay());
      System.out.println(d1.equals(d3));
   }
}
