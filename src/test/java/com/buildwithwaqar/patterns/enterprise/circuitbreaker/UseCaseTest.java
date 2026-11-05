package com.buildwithwaqar.patterns.enterprise.circuitbreaker;

import com.buildwithwaqar.patterns.enterprise.circuitbreaker.CircuitBreaker;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void opensAndRecovers() throws Exception {
    var now = new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
    var cb = new CircuitBreaker(1, Duration.ofSeconds(5), now::get);
    assertThrows(RuntimeException.class, () -> cb.call(() -> { throw new RuntimeException("x"); }));
    assertEquals(CircuitBreaker.State.OPEN, cb.state());
    now.set(now.get().plusSeconds(6));
    assertEquals("ok", cb.call(() -> "ok"));
  }
}
