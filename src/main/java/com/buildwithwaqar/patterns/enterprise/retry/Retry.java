package com.buildwithwaqar.patterns.enterprise.retry;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public final class Retry {
    private final int maxAttempts;
    private final Predicate<Exception> retryOn;

    public Retry(int maxAttempts, Predicate<Exception> retryOn) {
        this.maxAttempts = maxAttempts;
        this.retryOn = retryOn;
    }

    public <T> T execute(Callable<T> action) throws Exception {
        Exception last = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return action.call();
            } catch (Exception e) {
                last = e;
                if (i == maxAttempts || !retryOn.test(e)) throw e;
            }
        }
        throw last;
    }
}
