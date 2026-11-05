package com.buildwithwaqar.patterns.enterprise.outbox;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OutboxTest {
    @Test void publishesAfterCommit() {
        var outbox = new Outbox();
        outbox.placeOrder("o1", "{\"sku\":\"x\"}");
        assertEquals(1, outbox.unpublished().size());
        outbox.publishDue();
        assertTrue(outbox.unpublished().isEmpty());
        assertEquals(List.of("o1"), outbox.orders());
    }
}
