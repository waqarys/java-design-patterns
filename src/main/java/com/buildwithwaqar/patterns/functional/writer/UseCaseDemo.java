package com.buildwithwaqar.patterns.functional.writer;

import com.buildwithwaqar.patterns.functional.writer.Writer;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
/** Problem: compute a total while accumulating an audit trail. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Writer problem: audited total ===");
    var m = Monoids.STRING;
    var w = Writer.<String, Integer>pure(m, 0)
      .flatMap(m, n -> new Writer<>("add10;", n + 10))
      .flatMap(m, n -> new Writer<>("add5;", n + 5));
    System.out.println("value=" + w.value() + " log=" + w.log());
  }
}
