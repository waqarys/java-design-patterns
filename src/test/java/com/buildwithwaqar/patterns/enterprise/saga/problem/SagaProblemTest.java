package com.buildwithwaqar.patterns.enterprise.saga.problem;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SagaProblemTest {
    @Test void orchestrationCommitsWhenAllStepsSucceed() {
        var p = OrderBookingSaga.InMemoryPartners.alwaysOk();
        var saga = new OrderBookingSaga(p, p, p);
        assertTrue(saga.book("o1", "A", 1, 100, "X"));
        assertTrue(saga.log().contains("committed"));
    }

    @Test void orchestrationCompensatesInReverseWhenShippingFails() {
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> true, () -> false);
        var saga = new OrderBookingSaga(p, p, p);
        assertFalse(saga.book("o3", "A", 1, 100, "X"));
        assertTrue(p.reserved().isEmpty());
        assertTrue(p.charged().isEmpty());
        assertEquals(
                List.of("ok:reserve", "ok:charge", "abort:shipping:carrier_down",
                        "compensate:refund", "compensate:release", "rolled_back"),
                saga.log());
    }

    @Test void naiveLeaksReservationOnPaymentFailure() {
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> false, () -> true);
        assertFalse(new NaiveOrderFlow(p).book("o4", "A", 1, 100, "X"));
        assertEquals(List.of("o4"), p.reserved());
    }

    @Test void choreographyCommitsViaEventBus() {
        var bus = new EventBus();
        var p = OrderBookingSaga.InMemoryPartners.alwaysOk();
        var saga = new ChoreographyOrderSaga(bus, p);
        assertTrue(saga.book("c1", "A", 1, 100, "X"));
        assertTrue(saga.committed());
        assertTrue(bus.history().stream().anyMatch(e -> e instanceof EventBus.ShipmentCreated));
    }

    @Test void choreographyCompensatesWhenPaymentFails() {
        var bus = new EventBus();
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> false, () -> true);
        var saga = new ChoreographyOrderSaga(bus, p);
        assertFalse(saga.book("c2", "A", 1, 100, "X"));
        assertTrue(saga.rolledBack());
        assertTrue(p.reserved().isEmpty());
        assertTrue(p.charged().isEmpty());
        assertTrue(saga.log().contains("compensate:release"));
        assertTrue(bus.history().stream().anyMatch(e -> e instanceof EventBus.InventoryReleased));
    }
}
