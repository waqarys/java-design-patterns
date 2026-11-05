package com.buildwithwaqar.patterns.gof.structural.facade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {
    @Test void happyPath() {
        assertEquals("OK:TRACK-sku1", new OrderFacade().placeOrder("sku1", 1, "4111", 999));
    }

    @Test void inventoryReject() {
        assertEquals("REJECTED:inventory", new OrderFacade().placeOrder("", 1, "4111", 999));
    }
}
