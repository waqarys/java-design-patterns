package com.buildwithwaqar.patterns.enterprise.saga.problem;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SagaProblemTest {
    @Test void commitsWhenAllStepsSucceed() {
        var p = OrderBookingSaga.InMemoryPartners.alwaysOk();
        var saga = new OrderBookingSaga(p, p, p);
        assertTrue(saga.book("o1", "A", 1, 100, "X"));
        assertEquals(List.of("o1"), p.reserved());
        assertEquals(List.of("o1"), p.charged());
        assertEquals(List.of("o1"), p.shipped());
        assertTrue(saga.log().contains("committed"));
    }

    @Test void compensatesInReverseWhenPaymentFails() {
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> false, () -> true);
        var saga = new OrderBookingSaga(p, p, p);
        assertFalse(saga.book("o2", "A", 1, 100, "X"));
        assertTrue(p.reserved().isEmpty());
        assertTrue(p.charged().isEmpty());
        assertEquals(List.of("ok:reserve", "abort:payment:card_declined", "compensate:release", "rolled_back"), saga.log());
    }

    @Test void compensatesRefundThenReleaseWhenShippingFails() {
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> true, () -> false);
        var saga = new OrderBookingSaga(p, p, p);
        assertFalse(saga.book("o3", "A", 1, 100, "X"));
        assertTrue(p.reserved().isEmpty());
        assertTrue(p.charged().isEmpty());
        assertTrue(p.shipped().isEmpty());
        assertEquals(
                List.of("ok:reserve", "ok:charge", "abort:shipping:carrier_down",
                        "compensate:refund", "compensate:release", "rolled_back"),
                saga.log());
    }

    @Test void naiveLeaksReservationOnPaymentFailure() {
        var p = new OrderBookingSaga.InMemoryPartners(() -> true, () -> false, () -> true);
        assertFalse(new NaiveOrderFlow(p).book("o4", "A", 1, 100, "X"));
        assertEquals(List.of("o4"), p.reserved()); // leak — no compensation
    }
}
