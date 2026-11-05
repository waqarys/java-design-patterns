package com.buildwithwaqar.patterns.enterprise.circuitbreaker;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

class CircuitBreakerTest {
    @Test void opensAfterThreshold() throws Exception {
        var now = new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
        var cb = new CircuitBreaker(2, Duration.ofSeconds(30), now::get);
        assertThrows(RuntimeException.class, () -> cb.call(() -> { throw new RuntimeException("x"); }));
        assertThrows(RuntimeException.class, () -> cb.call(() -> { throw new RuntimeException("x"); }));
        assertEquals(CircuitBreaker.State.OPEN, cb.state());
        assertThrows(IllegalStateException.class, () -> cb.call(() -> "ok"));
        now.set(now.get().plusSeconds(31));
        assertEquals("ok", cb.call(() -> "ok"));
        assertEquals(CircuitBreaker.State.CLOSED, cb.state());
    }
}
