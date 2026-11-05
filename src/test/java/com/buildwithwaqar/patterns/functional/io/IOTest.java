package com.buildwithwaqar.patterns.functional.io;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class IOTest {
    @Test void defersEffects() {
        var hits = new AtomicInteger();
        IO<Integer> io = IO.delay(hits::incrementAndGet).map(n -> n + 10);
        assertEquals(0, hits.get());
        assertEquals(11, io.unsafeRun());
        assertEquals(1, hits.get());
    }
}
