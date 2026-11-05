package com.buildwithwaqar.patterns.concurrency.threadpool;

import com.buildwithwaqar.patterns.concurrency.threadpool.SimpleThreadPool;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: cap concurrent thumbnail jobs so the box is not fork-bombed. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Thread Pool problem: thumbnail jobs ===");
    var n = new AtomicInteger();
    try (var pool = new SimpleThreadPool(3)) {
      for (int i=0;i<12;i++) pool.submit(n::incrementAndGet);
      Thread.sleep(250);
    }
    System.out.println("done=" + n.get());
  }
}
