package com.buildwithwaqar.patterns.gof.creational.singleton;

import java.time.Instant;

/** Runnable walkthrough for the Singleton episode. */
public final class Demo {
    public static void main(String[] args) {
        System.out.println("=== Singleton walkthrough ===");
        AppConfig.INSTANCE.setEnv("staging");
        System.out.println("greeting=" + AppConfig.INSTANCE.greeting());
        System.out.println("sameEnum=" + (AppConfig.INSTANCE == AppConfig.INSTANCE));

        MetricsRegistry a = MetricsRegistry.getInstance();
        MetricsRegistry b = MetricsRegistry.getInstance();
        a.reset();
        a.hit(); a.hit(); a.hit();
        System.out.println("sameHolder=" + (a == b));
        System.out.println("hits=" + b.hits());

        Clock fixed = new Clock.FixedClock(Instant.parse("2026-01-01T00:00:00Z"));
        System.out.println("fixedNow=" + fixed.now());
    }
}
