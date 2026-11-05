package com.buildwithwaqar.patterns.functional.lazy;

import dev.zazr.Lazy;

import java.util.concurrent.atomic.AtomicInteger;

public final class LazyDemo {
    private LazyDemo() {}

    public static Lazy<Integer> expensive(AtomicInteger hits) {
        return Lazy.of(() -> {
            hits.incrementAndGet();
            return 42;
        });
    }
}
