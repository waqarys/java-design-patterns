package com.buildwithwaqar.patterns.concurrency.readwritelock;

import com.buildwithwaqar.patterns.concurrency.readwritelock.CachedValue;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: many threads read a CDN manifest; only one loads it. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Read-Write Lock problem: manifest cache ===");
    var loads = new AtomicInteger();
    var cache = new CachedValue<String>();
    System.out.println(cache.get(() -> { loads.incrementAndGet(); return "manifest-v3"; }));
    System.out.println(cache.get(() -> { loads.incrementAndGet(); return "nope"; }));
    System.out.println("loads=" + loads.get());
  }
}
