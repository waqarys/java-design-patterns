package com.buildwithwaqar.patterns.enterprise.retry;

import com.buildwithwaqar.patterns.enterprise.retry.Retry;
import java.util.concurrent.atomic.AtomicInteger;
/** Problem: publish to a flaky broker without failing the request on first blip. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Retry problem: flaky broker publish ===");
    var n = new AtomicInteger();
    var retry = new Retry(3, e -> e instanceof IllegalStateException);
    System.out.println("result=" + retry.execute(() -> {
      if (n.incrementAndGet() < 3) throw new IllegalStateException("timeout");
      return "PUBLISHED";
    }));
    System.out.println("attempts=" + n.get());
  }
}
