package com.buildwithwaqar.patterns.enterprise.retry;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class RetryTest {
    @Test void retriesThenSucceeds() throws Exception {
        var n = new AtomicInteger();
        var retry = new Retry(3, e -> e instanceof IllegalStateException);
        var result = retry.execute(() -> {
            if (n.incrementAndGet() < 3) throw new IllegalStateException("flaky");
            return "ok";
        });
        assertEquals("ok", result);
        assertEquals(3, n.get());
    }
}
