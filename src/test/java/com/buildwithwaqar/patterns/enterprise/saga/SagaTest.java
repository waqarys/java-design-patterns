package com.buildwithwaqar.patterns.enterprise.saga;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SagaTest {
    @Test void compensatesOnFailure() {
        var saga = new OrderSaga();
        saga.run(() -> false);
        assertTrue(saga.log().contains("aborted"));
        assertTrue(saga.log().contains("release"));
    }

    @Test void commitsWhenAllStepsPass() {
        var saga = new OrderSaga();
        saga.run(() -> true);
        assertTrue(saga.log().contains("shipped"));
        assertFalse(saga.log().contains("aborted"));
    }
}
