package com.buildwithwaqar.patterns.enterprise.saga.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Orchestration saga for: reserve inventory → charge payment → create shipment.
 * Each step registers a compensation; on failure compensations run in reverse order.
 * Steps are idempotent via a simple seen-set keyed by orderId+step.
 */
public final class OrderBookingSaga {
    public record StepResult(boolean ok, String detail) {}

    public interface Inventory { StepResult reserve(String orderId, String sku, int qty); void release(String orderId); }
    public interface Payments { StepResult charge(String orderId, int cents); void refund(String orderId); }
    public interface Shipping { StepResult ship(String orderId, String address); void recall(String orderId); }

    private final Inventory inventory;
    private final Payments payments;
    private final Shipping shipping;
    private final List<String> log = new ArrayList<>();
    private final List<String> seen = new ArrayList<>();

    public OrderBookingSaga(Inventory inventory, Payments payments, Shipping shipping) {
        this.inventory = inventory;
        this.payments = payments;
        this.shipping = shipping;
    }

    public List<String> log() { return List.copyOf(log); }

    public boolean book(String orderId, String sku, int qty, int cents, String address) {
        List<Runnable> compensations = new ArrayList<>();
        try {
            runOnce(orderId + ":reserve", () -> {
                var r = inventory.reserve(orderId, sku, qty);
                if (!r.ok()) throw new IllegalStateException("inventory:" + r.detail());
                log.add("ok:reserve");
                compensations.addFirst(() -> { inventory.release(orderId); log.add("compensate:release"); });
            });
            runOnce(orderId + ":charge", () -> {
                var r = payments.charge(orderId, cents);
                if (!r.ok()) throw new IllegalStateException("payment:" + r.detail());
                log.add("ok:charge");
                compensations.addFirst(() -> { payments.refund(orderId); log.add("compensate:refund"); });
            });
            runOnce(orderId + ":ship", () -> {
                var r = shipping.ship(orderId, address);
                if (!r.ok()) throw new IllegalStateException("shipping:" + r.detail());
                log.add("ok:ship");
                compensations.addFirst(() -> { shipping.recall(orderId); log.add("compensate:recall"); });
            });
            log.add("committed");
            return true;
        } catch (RuntimeException e) {
            log.add("abort:" + e.getMessage());
            for (var c : compensations) c.run();
            log.add("rolled_back");
            return false;
        }
    }

    private void runOnce(String key, Runnable action) {
        if (seen.contains(key)) {
            log.add("skip-idempotent:" + key);
            return;
        }
        action.run();
        seen.add(key);
    }

    /** In-memory collaborators with injectable failure points. */
    public static final class InMemoryPartners implements Inventory, Payments, Shipping {
        private final Supplier<Boolean> inventoryOk;
        private final Supplier<Boolean> paymentOk;
        private final Supplier<Boolean> shippingOk;
        private final List<String> reserved = new ArrayList<>();
        private final List<String> charged = new ArrayList<>();
        private final List<String> shipped = new ArrayList<>();

        public InMemoryPartners(Supplier<Boolean> inventoryOk, Supplier<Boolean> paymentOk, Supplier<Boolean> shippingOk) {
            this.inventoryOk = inventoryOk;
            this.paymentOk = paymentOk;
            this.shippingOk = shippingOk;
        }

        public static InMemoryPartners alwaysOk() {
            return new InMemoryPartners(() -> true, () -> true, () -> true);
        }

        public StepResult reserve(String orderId, String sku, int qty) {
            if (!inventoryOk.get()) return new StepResult(false, "out_of_stock");
            reserved.add(orderId);
            return new StepResult(true, sku + "x" + qty);
        }
        public void release(String orderId) { reserved.remove(orderId); }

        public StepResult charge(String orderId, int cents) {
            if (!paymentOk.get()) return new StepResult(false, "card_declined");
            charged.add(orderId);
            return new StepResult(true, "cents=" + cents);
        }
        public void refund(String orderId) { charged.remove(orderId); }

        public StepResult ship(String orderId, String address) {
            Objects.requireNonNull(address);
            if (!shippingOk.get()) return new StepResult(false, "carrier_down");
            shipped.add(orderId);
            return new StepResult(true, address);
        }
        public void recall(String orderId) { shipped.remove(orderId); }

        public List<String> reserved() { return List.copyOf(reserved); }
        public List<String> charged() { return List.copyOf(charged); }
        public List<String> shipped() { return List.copyOf(shipped); }
    }
}
