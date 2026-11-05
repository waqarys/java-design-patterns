package com.buildwithwaqar.patterns.gof.behavioral.interpreter.problem;

import java.util.Map;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Interpreter: pricing-rule DSL ===");
        var ctx = Map.of("SKU", 2000, "ADDON", 500);
        System.out.println("--- naive ---");
        System.out.println(NaivePricing.price("STACK", ctx));
        System.out.println("--- DSL AST ---");
        PricingDsl.Rule rule = new PricingDsl.FlatOff(
                new PricingDsl.PercentOff(new PricingDsl.Base("SKU"), 10), 200);
        System.out.println("price=" + rule.apply(ctx));
        PricingDsl.Rule stack = new PricingDsl.Stack(rule, new PricingDsl.Base("ADDON"));
        System.out.println("cart=" + stack.apply(ctx));
        try {
            new PricingDsl.Base("NOPE").apply(ctx);
        } catch (IllegalArgumentException e) {
            System.out.println("caught=" + e.getMessage());
        }
    }
}
