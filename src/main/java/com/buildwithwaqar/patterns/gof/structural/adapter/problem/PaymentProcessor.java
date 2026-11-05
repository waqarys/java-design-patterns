package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/** Target interface our checkout code depends on. */
public interface PaymentProcessor {
    PaymentResult charge(String customerId, int amountCents, String currency);
}

record PaymentResult(boolean ok, String transactionId, String message) {}
