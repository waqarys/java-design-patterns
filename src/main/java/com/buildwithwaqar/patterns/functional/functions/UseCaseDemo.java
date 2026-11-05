package com.buildwithwaqar.patterns.functional.functions;

import com.buildwithwaqar.patterns.functional.functions.Fn;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: build a pricing pipeline and memoize hot square lookups. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Currying/Memoization problem ===");
    var add = Fn.curry((Integer x, Integer y) -> x + y);
    System.out.println("curry=" + add.apply(2).apply(40));
    var hits = new AtomicInteger();
    var memo = Fn.memoize((Integer x) -> { hits.incrementAndGet(); return x * x; });
    System.out.println("memo=" + memo.apply(12) + "," + memo.apply(12) + " hits=" + hits.get());
  }
}
