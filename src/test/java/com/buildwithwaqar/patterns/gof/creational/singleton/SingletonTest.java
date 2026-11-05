package com.buildwithwaqar.patterns.gof.creational.singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    @BeforeEach
    void reset() {
        AppConfig.INSTANCE.setEnv("prod");
        MetricsRegistry.getInstance().reset();
    }

    @Test
    void enumSingletonIsUniqueAndMutable() {
        assertSame(AppConfig.INSTANCE, AppConfig.INSTANCE);
        AppConfig.INSTANCE.setEnv("staging");
        assertEquals("hello from staging", AppConfig.INSTANCE.greeting());
    }

    @Test
    void holderIsUniqueAcrossVirtualThreads() throws Exception {
        var a = new AtomicReference<MetricsRegistry>();
        var b = new AtomicReference<MetricsRegistry>();
        var start = new CountDownLatch(1);
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            var f1 = pool.submit(() -> { start.await(); a.set(MetricsRegistry.getInstance()); return null; });
            var f2 = pool.submit(() -> { start.await(); b.set(MetricsRegistry.getInstance()); return null; });
            start.countDown();
            f1.get(); f2.get();
        }
        assertSame(a.get(), b.get());
        a.get().hit(); a.get().hit();
        assertEquals(2, b.get().hits());
    }

    @Test
    void injectedClockIsTestable() {
        Clock clock = new Clock.FixedClock(Instant.parse("2026-01-01T00:00:00Z"));
        assertEquals(Instant.parse("2026-01-01T00:00:00Z"), clock.now());
    }
}
