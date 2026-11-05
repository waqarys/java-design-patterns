package com.buildwithwaqar.patterns.functional.functions;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Functions walkthrough ===");
    var add = Fn.curry((Integer x, Integer y) -> x + y);
    System.out.println("curry=" + add.apply(2).apply(3));
    var pipeline = Fn.compose((Integer x) -> x * 2, (Integer x) -> x + 1);
    System.out.println("compose=" + pipeline.apply(3));
    var hits = new AtomicInteger();
    var memo = Fn.memoize((Integer x) -> { hits.incrementAndGet(); return x * x; });
    System.out.println("memo1=" + memo.apply(3) + " memo2=" + memo.apply(3) + " hits=" + hits.get());
  }
}
