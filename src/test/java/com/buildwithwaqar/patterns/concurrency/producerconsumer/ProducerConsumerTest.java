package com.buildwithwaqar.patterns.concurrency.producerconsumer;

import org.junit.jupiter.api.Test;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.*;

class ProducerConsumerTest {
    @Test void transfersItems() throws Exception {
        var q = new BoundedQueue<Integer>(2);
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> p = pool.submit(() -> { for (int i = 0; i < 5; i++) q.put(i); return null; });
            var sum = pool.submit(() -> {
                int s = 0;
                for (int i = 0; i < 5; i++) s += q.take();
                return s;
            });
            p.get();
            assertEquals(10, sum.get());
        }
    }
}
