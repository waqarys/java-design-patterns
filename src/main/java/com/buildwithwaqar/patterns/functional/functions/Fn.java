package com.buildwithwaqar.patterns.functional.functions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class Fn {
    private Fn() {}

    public static <A, B, C> Function<A, Function<B, C>> curry(java.util.function.BiFunction<A, B, C> f) {
        return a -> b -> f.apply(a, b);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
        return a -> g.apply(f.apply(a));
    }

    public static <A, B> Function<A, B> memoize(Function<A, B> f) {
        Map<A, B> cache = new ConcurrentHashMap<>();
        return a -> cache.computeIfAbsent(a, f);
    }
}
