package com.buildwithwaqar.patterns.gof.behavioral.interpreter.problem;

import java.util.Map;

/** Naive: stringly if/else discount codes — untestable composition. */
public final class NaivePricing {
    public static int price(String code, Map<String, Integer> prices) {
        int base = prices.getOrDefault("SKU", 0);
        if ("PCT10".equals(code)) return base * 90 / 100;
        if ("FLAT200".equals(code)) return Math.max(0, base - 200);
        if ("STACK".equals(code)) return Math.max(0, base * 90 / 100 - 200);
        throw new IllegalArgumentException("unknown " + code);
    }
}
