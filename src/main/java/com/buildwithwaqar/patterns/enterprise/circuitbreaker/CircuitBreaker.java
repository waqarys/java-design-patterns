package com.buildwithwaqar.patterns.enterprise.circuitbreaker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class CircuitBreaker {
    public enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final Duration openTimeout;
    private final Supplier<Instant> clock;

    private State state = State.CLOSED;
    private int failures;
    private Instant openedAt = Instant.EPOCH;

    public CircuitBreaker(int failureThreshold, Duration openTimeout, Supplier<Instant> clock) {
        this.failureThreshold = failureThreshold;
        this.openTimeout = openTimeout;
        this.clock = clock;
    }

    public State state() { return state; }

    public <T> T call(Callable<T> action) throws Exception {
        if (state == State.OPEN) {
            if (clock.get().isAfter(openedAt.plus(openTimeout))) {
                state = State.HALF_OPEN;
            } else {
                throw new IllegalStateException("circuit open");
            }
        }
        try {
            var result = action.call();
            failures = 0;
            state = State.CLOSED;
            return result;
        } catch (Exception e) {
            failures++;
            if (failures >= failureThreshold || state == State.HALF_OPEN) {
                state = State.OPEN;
                openedAt = clock.get();
            }
            throw e;
        }
    }
}
