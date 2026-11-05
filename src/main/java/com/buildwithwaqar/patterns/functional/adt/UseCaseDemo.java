package com.buildwithwaqar.patterns.functional.adt;

import com.buildwithwaqar.patterns.functional.adt.*;
/** Problem: payments that cannot encode illegal field combinations; emails that validate. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== ADT/Smart Constructor problem ===");
    System.out.println(Email.parse("waqar@bwq.dev"));
    System.out.println(Email.parse("bad"));
    System.out.println(Payment.describe(new CardPayment(999, "4242")));
  }
}
