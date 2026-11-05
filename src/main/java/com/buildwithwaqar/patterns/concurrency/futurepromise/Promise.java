package com.buildwithwaqar.patterns.concurrency.futurepromise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/** Promise/future demo using CompletableFuture (Java's standard promise). */
public final class Promise {
    private Promise() {}

    public static CompletionStage<Integer> computeAsync(int x) {
        return CompletableFuture.supplyAsync(() -> x * 2);
    }

    public static Future<Integer> asFuture(int x) {
        return computeAsync(x).toCompletableFuture();
    }
}
