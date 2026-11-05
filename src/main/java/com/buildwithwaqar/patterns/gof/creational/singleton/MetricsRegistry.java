package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Lazy holder idiom — class loading provides thread-safe lazy init. */
public final class MetricsRegistry {
    private MetricsRegistry() {}

    private static final class Holder {
        static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() { return Holder.INSTANCE; }

    private long hits;

    public synchronized void hit() { hits++; }
    public synchronized long hits() { return hits; }
    synchronized void reset() { hits = 0; }
}
