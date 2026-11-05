package com.buildwithwaqar.patterns.enterprise.circuitbreaker;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Circuit Breaker walkthrough ===");
    var now = new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
    var cb = new CircuitBreaker(2, Duration.ofSeconds(30), now::get);
    for (int i = 0; i < 2; i++) {
      try { cb.call(() -> { throw new RuntimeException("down"); }); }
      catch (Exception e) { System.out.println("fail" + i + " state=" + cb.state()); }
    }
    try { cb.call(() -> "ok"); } catch (Exception e) { System.out.println("shortCircuit=" + e.getMessage()); }
    now.set(now.get().plusSeconds(31));
    System.out.println("afterTimeout=" + cb.call(() -> "ok") + " state=" + cb.state());
  }
}
