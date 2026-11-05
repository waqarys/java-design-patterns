package com.buildwithwaqar.patterns.functional.lazy;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Lazy walkthrough ===");
    var hits = new AtomicInteger();
    var lazy = LazyDemo.expensive(hits);
    System.out.println("hitsBefore=" + hits.get());
    System.out.println("v1=" + lazy.get());
    System.out.println("v2=" + lazy.get());
    System.out.println("hitsAfter=" + hits.get());
  }
}
