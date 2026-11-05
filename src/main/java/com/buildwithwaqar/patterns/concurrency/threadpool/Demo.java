package com.buildwithwaqar.patterns.concurrency.threadpool;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Thread Pool walkthrough ===");
    var n = new AtomicInteger();
    try (var pool = new SimpleThreadPool(2)) {
      for (int i = 0; i < 8; i++) pool.submit(n::incrementAndGet);
      Thread.sleep(300);
    }
    System.out.println("completed=" + n.get());
  }
}
