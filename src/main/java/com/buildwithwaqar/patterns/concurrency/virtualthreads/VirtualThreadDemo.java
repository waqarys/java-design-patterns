package com.buildwithwaqar.patterns.concurrency.virtualthreads;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/** Java 21+ virtual threads — prefer over hand-rolled pools for blocking work. */
public final class VirtualThreadDemo {
    private VirtualThreadDemo() {}

    public static int fanOut(int tasks) throws InterruptedException {
        var hits = new AtomicInteger();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < tasks; i++) {
                pool.submit(hits::incrementAndGet);
            }
        }
        return hits.get();
    }
}
