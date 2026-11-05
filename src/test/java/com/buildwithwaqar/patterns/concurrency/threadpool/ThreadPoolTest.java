package com.buildwithwaqar.patterns.concurrency.threadpool;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolTest {
    @Test void runsTasks() throws Exception {
        var n = new AtomicInteger();
        try (var pool = new SimpleThreadPool(2)) {
            for (int i = 0; i < 10; i++) pool.submit(n::incrementAndGet);
            Thread.sleep(200);
        }
        assertEquals(10, n.get());
    }
}
