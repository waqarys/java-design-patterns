package com.buildwithwaqar.patterns.functional.adt;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== ADT / Smart Constructor walkthrough ===");
    System.out.println("email=" + Email.parse("waqar@bwq.dev"));
    System.out.println("badEmail=" + Email.parse("nope"));
    System.out.println(Payment.describe(new CashPayment(100)));
    System.out.println(Payment.describe(new CardPayment(500, "1234")));
    System.out.println(Payment.describe(new WirePayment(900, "IN00")));
  }
}
