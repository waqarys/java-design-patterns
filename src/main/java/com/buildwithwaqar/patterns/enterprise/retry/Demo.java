package com.buildwithwaqar.patterns.enterprise.retry;
import java.util.concurrent.atomic.AtomicInteger;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Retry walkthrough ===");
    var n = new AtomicInteger();
    var retry = new Retry(3, e -> e instanceof IllegalStateException);
    var result = retry.execute(() -> {
      int v = n.incrementAndGet();
      System.out.println("attempt=" + v);
      if (v < 3) throw new IllegalStateException("flaky");
      return "published";
    });
    System.out.println("result=" + result);
  }
}
