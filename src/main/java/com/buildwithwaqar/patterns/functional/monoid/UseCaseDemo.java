package com.buildwithwaqar.patterns.functional.monoid;

import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import java.util.List;
/** Problem: fold invoice line totals and concatenate audit fragments. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Monoid problem: fold totals/logs ===");
    System.out.println("total=" + Monoids.INT_SUM.fold(List.of(100,250,50)));
    System.out.println("audit=" + Monoids.STRING.fold(List.of("reserve;", "charge;", "ship;")));
  }
}
