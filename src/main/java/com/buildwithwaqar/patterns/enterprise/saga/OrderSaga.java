package com.buildwithwaqar.patterns.enterprise.saga;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Minimal choreography-style saga with compensations. */
public final class OrderSaga {
    private final List<Runnable> compensations = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public void step(String name, Runnable action, Runnable compensate) {
        action.run();
        log.add("ok:" + name);
        compensations.addFirst(compensate);
    }

    public void run(Supplier<Boolean> paymentOk) {
        try {
            step("reserve", () -> log.add("reserved"), () -> log.add("release"));
            step("charge", () -> {
                if (!paymentOk.get()) throw new IllegalStateException("pay");
                log.add("charged");
            }, () -> log.add("refund"));
            step("ship", () -> log.add("shipped"), () -> log.add("recall"));
        } catch (RuntimeException e) {
            for (var c : compensations) c.run();
            log.add("aborted");
        }
    }

    List<String> log() { return List.copyOf(log); }
}
