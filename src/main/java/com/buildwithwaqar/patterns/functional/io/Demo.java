package com.buildwithwaqar.patterns.functional.io;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== IO walkthrough ===");
    var hits = new AtomicInteger();
    IO<Integer> io = IO.delay(hits::incrementAndGet).map(n -> n + 10);
    System.out.println("hitsBefore=" + hits.get());
    System.out.println("run=" + io.unsafeRun());
    System.out.println("hitsAfter=" + hits.get());
  }
}
