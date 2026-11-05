package com.buildwithwaqar.patterns.concurrency.readwritelock;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Read-Write Lock walkthrough ===");
    var loads = new AtomicInteger();
    var cache = new CachedValue<String>();
    System.out.println(cache.get(() -> { loads.incrementAndGet(); return "manifest-v1"; }));
    System.out.println(cache.get(() -> { loads.incrementAndGet(); return "manifest-v2"; }));
    System.out.println("loads=" + loads.get());
  }
}
