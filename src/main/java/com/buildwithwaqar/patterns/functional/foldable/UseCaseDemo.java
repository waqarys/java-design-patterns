package com.buildwithwaqar.patterns.functional.foldable;

import com.buildwithwaqar.patterns.functional.foldable.Foldable;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import java.util.List;
/** Problem: generic fold of scores with a monoid. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Foldable problem: score totals ===");
    System.out.println(Foldable.fold(List.of(10,20,30), Monoids.INT_SUM));
  }
}
