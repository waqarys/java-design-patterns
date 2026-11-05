package com.buildwithwaqar.patterns.functional.memoization.problem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/** Memoized coin-change DP + tax-rate lookup with call-count / timing traces. */
public final class CoinChangeMemo {
    private CoinChangeMemo() {}

    public static final class Counter {
        private final AtomicInteger calls = new AtomicInteger();
        public int inc() { return calls.incrementAndGet(); }
        public int get() { return calls.get(); }
        public void reset() { calls.set(0); }
    }

    /** Naive exponential recursion. */
    public static int naiveWays(int amount, int[] coins, Counter c) {
        c.inc();
        if (amount == 0) return 1;
        if (amount < 0 || coins.length == 0) return 0;
        int with = naiveWays(amount - coins[0], coins, c);
        int without = naiveWays(amount, Arrays.copyOfRange(coins, 1, coins.length), c);
        return with + without;
    }

    public static int memoWays(int amount, int[] coins, Counter c) {
        return memoWays(amount, coins, 0, new HashMap<>(), c);
    }

    private static int memoWays(int amount, int[] coins, int i, Map<String, Integer> cache, Counter c) {
        c.inc();
        if (amount == 0) return 1;
        if (amount < 0 || i >= coins.length) return 0;
        String key = amount + ":" + i;
        Integer hit = cache.get(key);
        if (hit != null) return hit;
        int with = memoWays(amount - coins[i], coins, i, cache, c);
        int without = memoWays(amount, coins, i + 1, cache, c);
        int ans = with + without;
        cache.put(key, ans);
        return ans;
    }

    /** Generic memoize wrapper for expensive lookups. */
    public static <A, B> Function<A, B> memoize(Function<A, B> f, Counter c) {
        Map<A, B> cache = new HashMap<>();
        return a -> {
            if (cache.containsKey(a)) return cache.get(a);
            c.inc();
            B b = f.apply(a);
            cache.put(a, b);
            return b;
        };
    }

    public static int expensiveTaxRate(String region) {
        try { Thread.sleep(5); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return switch (region) {
            case "CA" -> 725; case "NY" -> 800; default -> 500;
        };
    }
}
