package com.buildwithwaqar.patterns.gof.behavioral.interpreter.problem;

import java.util.Map;

/** Pricing-rule DSL: Base / PercentOff / FlatOff / Stack as an Expression AST. */
public final class PricingDsl {
    private PricingDsl() {}
    public sealed interface Rule permits Base, PercentOff, FlatOff, Stack {
        int apply(Map<String, Integer> ctx);
    }
    public record Base(String skuKey) implements Rule {
        public int apply(Map<String, Integer> ctx) {
            Integer v = ctx.get(skuKey);
            if (v == null) throw new IllegalArgumentException("unknown sku " + skuKey);
            return v;
        }
    }
    public record PercentOff(Rule inner, int percent) implements Rule {
        public int apply(Map<String, Integer> ctx) {
            if (percent < 0 || percent > 100) throw new IllegalArgumentException("bad percent");
            return inner.apply(ctx) * (100 - percent) / 100;
        }
    }
    public record FlatOff(Rule inner, int cents) implements Rule {
        public int apply(Map<String, Integer> ctx) {
            return Math.max(0, inner.apply(ctx) - cents);
        }
    }
    public record Stack(Rule left, Rule right) implements Rule {
        public int apply(Map<String, Integer> ctx) { return left.apply(ctx) + right.apply(ctx); }
    }
}
