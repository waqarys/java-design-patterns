package com.buildwithwaqar.patterns.gof.behavioral.strategy;

import java.util.function.IntUnaryOperator;

interface PricingStrategy {
    int priceCents(int baseCents);

    static PricingStrategy percentageOff(int percent) {
        return base -> base - base * percent / 100;
    }

    static PricingStrategy flatOff(int cents) {
        return base -> Math.max(0, base - cents);
    }
}

/** Context that delegates to a pluggable pricing strategy. */
public final class Cart {
    private final PricingStrategy strategy;

    public Cart(PricingStrategy strategy) { this.strategy = strategy; }

    /** Java 25 style: accept a function directly. */
    public Cart(IntUnaryOperator fn) { this.strategy = fn::applyAsInt; }

    public int checkout(int baseCents) { return strategy.priceCents(baseCents); }
}
