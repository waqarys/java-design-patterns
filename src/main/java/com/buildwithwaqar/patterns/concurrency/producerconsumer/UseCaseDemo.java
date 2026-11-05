package com.buildwithwaqar.patterns.concurrency.producerconsumer;

import com.buildwithwaqar.patterns.concurrency.producerconsumer.BoundedQueue;
import java.util.concurrent.*;
/** Problem: encoder produces frames; uploader consumes without unbounded memory. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Producer-Consumer problem: bounded frame queue ===");
    var q = new BoundedQueue<Integer>(2);
    try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
      pool.submit(() -> { for (int i=1;i<=4;i++){ q.put(i); System.out.println("encode "+i);} return null; });
      var sum = pool.submit(() -> { int s=0; for(int i=0;i<4;i++){ int v=q.take(); System.out.println("upload "+v); s+=v;} return s;});
      System.out.println("checksum=" + sum.get());
    }
  }
}
