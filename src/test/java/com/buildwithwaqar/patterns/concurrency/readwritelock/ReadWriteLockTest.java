package com.buildwithwaqar.patterns.concurrency.readwritelock;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class ReadWriteLockTest {
    @Test void loadsOnce() {
        var loads = new AtomicInteger();
        var cache = new CachedValue<String>();
        assertEquals("x", cache.get(() -> { loads.incrementAndGet(); return "x"; }));
        assertEquals("x", cache.get(() -> { loads.incrementAndGet(); return "y"; }));
        assertEquals(1, loads.get());
    }
}
