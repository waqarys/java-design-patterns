package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdapterProblemTest {
    @Test void adapterChargesThroughLegacyGateway() {
        var checkout = new CheckoutService(new SoapPaymentAdapter(new LegacySoapGateway()));
        assertTrue(checkout.checkout("alice", 1999).startsWith("OK:SOAP-alice-"));
    }

    @Test void rejectsNonPositive() {
        var checkout = new CheckoutService(new SoapPaymentAdapter(new LegacySoapGateway()));
        assertTrue(checkout.checkout("alice", 0).startsWith("FAILED:"));
    }

    @Test void rejectsNonUsd() {
        var r = new SoapPaymentAdapter(new LegacySoapGateway()).charge("a", 100, "INR");
        assertFalse(r.ok());
        assertTrue(r.message().contains("currency"));
    }

    @Test void naiveAlsoWorksButCouplesXml() {
        assertTrue(new NaiveCheckout().pay("bob", 500).startsWith("OK:"));
    }
}
