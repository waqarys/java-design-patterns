package com.buildwithwaqar.patterns.functional.lazy;

import com.buildwithwaqar.patterns.functional.lazy.LazyDemo;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: defer expensive report generation until someone asks. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Lazy problem: deferred report ===");
    var hits = new AtomicInteger();
    var lazy = LazyDemo.expensive(hits);
    System.out.println("before=" + hits.get());
    System.out.println("v=" + lazy.get() + " after=" + hits.get() + " again=" + lazy.get() + " hits=" + hits.get());
  }
}
