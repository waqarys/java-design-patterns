package com.buildwithwaqar.patterns.functional.writer;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Writer walkthrough ===");
    var m = Monoids.STRING;
    Writer<String, Integer> w = Writer.<String, Integer>pure(m, 1)
      .flatMap(m, n -> new Writer<>("inc;", n + 1))
      .flatMap(m, n -> new Writer<>("dbl;", n * 2));
    System.out.println("value=" + w.value() + " log=" + w.log());
  }
}
