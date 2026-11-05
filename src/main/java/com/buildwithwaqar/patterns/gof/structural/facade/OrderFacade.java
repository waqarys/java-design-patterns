package com.buildwithwaqar.patterns.gof.structural.facade;

final class Inventory { boolean reserve(String sku, int qty) { return qty > 0 && !sku.isBlank(); } }
final class PaymentGateway { boolean charge(String card, int cents) { return card != null && cents > 0; } }
final class Shipping { String ship(String sku) { return "TRACK-" + sku; } }

/** Simple interface over a noisy subsystem. */
public final class OrderFacade {
    private final Inventory inventory = new Inventory();
    private final PaymentGateway payments = new PaymentGateway();
    private final Shipping shipping = new Shipping();

    public String placeOrder(String sku, int qty, String card, int cents) {
        if (!inventory.reserve(sku, qty)) return "REJECTED:inventory";
        if (!payments.charge(card, cents)) return "REJECTED:payment";
        return "OK:" + shipping.ship(sku);
    }
}
