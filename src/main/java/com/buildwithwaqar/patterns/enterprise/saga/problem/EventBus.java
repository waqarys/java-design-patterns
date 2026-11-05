package com.buildwithwaqar.patterns.enterprise.saga.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** In-memory event bus for choreography-style sagas. */
public final class EventBus {
    public sealed interface Event permits
            InventoryReserved, InventoryReleased, PaymentCharged, PaymentRefunded,
            ShipmentCreated, ShipmentRecalled, StepFailed {

        String orderId();
    }

    public record InventoryReserved(String orderId, String sku, int qty) implements Event {}
    public record InventoryReleased(String orderId) implements Event {}
    public record PaymentCharged(String orderId, int cents) implements Event {}
    public record PaymentRefunded(String orderId) implements Event {}
    public record ShipmentCreated(String orderId, String address) implements Event {}
    public record ShipmentRecalled(String orderId) implements Event {}
    public record StepFailed(String orderId, String step, String reason) implements Event {}

    private final Map<Class<? extends Event>, List<Consumer<Event>>> handlers = new HashMap<>();
    private final List<Event> history = new ArrayList<>();

    public synchronized <E extends Event> void subscribe(Class<E> type, Consumer<E> handler) {
        handlers.computeIfAbsent(type, k -> new ArrayList<>())
                .add(e -> handler.accept(type.cast(e)));
    }

    public synchronized void publish(Event event) {
        history.add(event);
        for (var h : handlers.getOrDefault(event.getClass(), List.of())) {
            h.accept(event);
        }
    }

    public synchronized List<Event> history() { return List.copyOf(history); }
}
