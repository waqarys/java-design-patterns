package com.buildwithwaqar.patterns.functional.lazy;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class LazyDemoTest {
    @Test void evaluatesOnce() {
        var hits = new AtomicInteger();
        var lazy = LazyDemo.expensive(hits);
        assertEquals(0, hits.get());
        assertEquals(42, lazy.get());
        assertEquals(42, lazy.get());
        assertEquals(1, hits.get());
    }
}
