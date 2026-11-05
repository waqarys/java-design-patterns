package com.buildwithwaqar.patterns.functional.monoid.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Monoid: fold invoice totals ===");
    System.out.println("--- naive ---"); System.out.println(NaiveSum.sum(List.of(10,20,30)));
System.out.println("--- monoid ---");
System.out.println(Monoids.fold(Monoids.INT_ADD, List.of(10,20,30)));
System.out.println(Monoids.fold(Monoids.STRING, List.of("a","b","c")));
  }
}
