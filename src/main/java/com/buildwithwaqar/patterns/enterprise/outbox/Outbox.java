package com.buildwithwaqar.patterns.enterprise.outbox;

import java.util.ArrayList;
import java.util.List;

record OutboxMessage(String id, String topic, String payload, boolean published) {
    OutboxMessage markPublished() { return new OutboxMessage(id, topic, payload, true); }
}

/** Transactional outbox — persist business row + message atomically, publish async. */
public final class Outbox {
    private final List<String> orders = new ArrayList<>();
    private final List<OutboxMessage> messages = new ArrayList<>();

    public synchronized void placeOrder(String orderId, String payload) {
        orders.add(orderId);
        messages.add(new OutboxMessage(orderId, "orders", payload, false));
    }

    public synchronized List<OutboxMessage> unpublished() {
        return messages.stream().filter(m -> !m.published()).toList();
    }

    public synchronized void publishDue() {
        for (int i = 0; i < messages.size(); i++) {
            var m = messages.get(i);
            if (!m.published()) messages.set(i, m.markPublished());
        }
    }

    public List<String> orders() { return List.copyOf(orders); }
}
