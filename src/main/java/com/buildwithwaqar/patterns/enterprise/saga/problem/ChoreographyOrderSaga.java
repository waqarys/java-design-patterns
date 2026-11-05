package com.buildwithwaqar.patterns.enterprise.saga.problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Choreography saga: services react to events on an in-memory bus.
 * Inventory publishes InventoryReserved after reserve;
 * Payment reacts and publishes PaymentCharged (or StepFailed);
 * Shipping reacts and publishes ShipmentCreated (or StepFailed);
 * StepFailed triggers reverse compensations via the same bus.
 */
public final class ChoreographyOrderSaga {
    private final EventBus bus;
    private final OrderBookingSaga.InMemoryPartners partners;
    private final List<String> log = new ArrayList<>();
    private boolean committed;
    private boolean rolledBack;
    private int pendingCents;
    private String pendingAddress;

    public ChoreographyOrderSaga(EventBus bus, OrderBookingSaga.InMemoryPartners partners) {
        this.bus = bus;
        this.partners = partners;
        wire();
    }

    public List<String> log() { return List.copyOf(log); }
    public boolean committed() { return committed; }
    public boolean rolledBack() { return rolledBack; }

    private void wire() {
        bus.subscribe(EventBus.InventoryReserved.class, e -> {
            log.add("react:payment-on-reserved");
            var r = partners.charge(e.orderId(), pendingCents);
            if (!r.ok()) {
                bus.publish(new EventBus.StepFailed(e.orderId(), "charge", r.detail()));
                return;
            }
            log.add("ok:charge");
            bus.publish(new EventBus.PaymentCharged(e.orderId(), pendingCents));
        });
        bus.subscribe(EventBus.PaymentCharged.class, e -> {
            log.add("react:ship-on-charged");
            var r = partners.ship(e.orderId(), pendingAddress);
            if (!r.ok()) {
                bus.publish(new EventBus.StepFailed(e.orderId(), "ship", r.detail()));
                return;
            }
            log.add("ok:ship");
            bus.publish(new EventBus.ShipmentCreated(e.orderId(), pendingAddress));
            committed = true;
            log.add("committed");
        });
        bus.subscribe(EventBus.StepFailed.class, e -> {
            log.add("abort:" + e.step() + ":" + e.reason());
            if (partners.shipped().contains(e.orderId())) {
                partners.recall(e.orderId());
                bus.publish(new EventBus.ShipmentRecalled(e.orderId()));
                log.add("compensate:recall");
            }
            if (partners.charged().contains(e.orderId())) {
                partners.refund(e.orderId());
                bus.publish(new EventBus.PaymentRefunded(e.orderId()));
                log.add("compensate:refund");
            }
            if (partners.reserved().contains(e.orderId())) {
                partners.release(e.orderId());
                bus.publish(new EventBus.InventoryReleased(e.orderId()));
                log.add("compensate:release");
            }
            rolledBack = true;
            log.add("rolled_back");
        });
    }

    public boolean book(String orderId, String sku, int qty, int cents, String address) {
        pendingCents = cents;
        pendingAddress = address;
        var r = partners.reserve(orderId, sku, qty);
        if (!r.ok()) {
            bus.publish(new EventBus.StepFailed(orderId, "reserve", r.detail()));
            return false;
        }
        log.add("ok:reserve");
        bus.publish(new EventBus.InventoryReserved(orderId, sku, qty));
        return committed;
    }
}
