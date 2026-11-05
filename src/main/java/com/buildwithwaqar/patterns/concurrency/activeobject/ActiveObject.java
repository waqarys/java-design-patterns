package com.buildwithwaqar.patterns.concurrency.activeobject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Active Object — all calls run on a dedicated async servant thread. */
public final class ActiveObject implements AutoCloseable {
    private final ExecutorService servant = Executors.newSingleThreadExecutor(r -> {
        var t = new Thread(r, "active-object");
        t.setDaemon(true);
        return t;
    });
    private int value;

    public Future<Integer> add(int delta) {
        return servant.submit(() -> { value += delta; return value; });
    }

    public Future<Integer> get() {
        return servant.submit((Callable<Integer>) () -> value);
    }

    @Override public void close() { servant.shutdownNow(); }
}
