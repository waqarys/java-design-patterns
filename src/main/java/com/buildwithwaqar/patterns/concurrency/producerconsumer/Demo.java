package com.buildwithwaqar.patterns.concurrency.producerconsumer;
import java.util.concurrent.*;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Producer-Consumer walkthrough ===");
    var q = new BoundedQueue<Integer>(2);
    try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
      pool.submit(() -> { for (int i = 1; i <= 5; i++) { q.put(i); System.out.println("put " + i); } return null; });
      var sum = pool.submit(() -> { int s=0; for (int i=0;i<5;i++){ int v=q.take(); System.out.println("take "+v); s+=v;} return s; });
      System.out.println("sum=" + sum.get());
    }
  }
}
