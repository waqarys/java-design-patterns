package com.buildwithwaqar.patterns.gof.creational.singleton;

import java.time.Instant;

/** Prefer injecting a Clock over a time singleton. */
public interface Clock {
    Instant now();

    record SystemClock() implements Clock {
        @Override public Instant now() { return Instant.now(); }
    }

    record FixedClock(Instant instant) implements Clock {
        @Override public Instant now() { return instant; }
    }
}
