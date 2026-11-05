package com.buildwithwaqar.patterns.enterprise.circuitbreaker;

import com.buildwithwaqar.patterns.enterprise.circuitbreaker.CircuitBreaker;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
/** Problem: stop hammering a flaky rates HTTP API; recover after timeout. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Circuit Breaker problem: flaky rates API ===");
    var now = new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
    var cb = new CircuitBreaker(2, Duration.ofSeconds(30), now::get);
    for (int i=0;i<2;i++) try { cb.call(() -> { throw new RuntimeException("503"); }); } catch (Exception e) { System.out.println("fail state="+cb.state()); }
    try { cb.call(() -> "ok"); } catch (Exception e) { System.out.println("shortCircuit="+e.getMessage()); }
    now.set(now.get().plusSeconds(31));
    System.out.println("recovered="+cb.call(() -> "rate=83.2")+" state="+cb.state());
  }
}
