package com.buildwithwaqar.patterns.functional.monoid;
import java.util.List;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Monoid walkthrough ===");
    System.out.println("sum=" + Monoids.INT_SUM.fold(List.of(1,2,3,4)));
    System.out.println("str=" + Monoids.STRING.fold(List.of("Build"," ","with"," ","Waqar")));
  }
}
