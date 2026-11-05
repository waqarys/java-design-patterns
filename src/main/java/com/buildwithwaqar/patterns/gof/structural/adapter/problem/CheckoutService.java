package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/** Clean client — only knows PaymentProcessor. */
public final class CheckoutService {
    private final PaymentProcessor processor;

    public CheckoutService(PaymentProcessor processor) {
        this.processor = processor;
    }

    public String checkout(String customerId, int amountCents) {
        PaymentResult r = processor.charge(customerId, amountCents, "USD");
        return r.ok() ? "OK:" + r.transactionId() : "FAILED:" + r.message();
    }
}
