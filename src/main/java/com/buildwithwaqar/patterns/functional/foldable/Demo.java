package com.buildwithwaqar.patterns.functional.foldable;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import java.util.List;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Foldable walkthrough ===");
    System.out.println("foldLeft=" + Foldable.foldLeft(List.of(1,2,3), 0, Integer::sum));
    System.out.println("foldMonoid=" + Foldable.fold(List.of(1,2,3), Monoids.INT_SUM));
  }
}
