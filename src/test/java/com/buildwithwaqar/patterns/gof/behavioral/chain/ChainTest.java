package com.buildwithwaqar.patterns.gof.behavioral.chain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChainTest {
    @Test void routesAlongChain() {
        var root = new BillingHandler();
        root.linkWith(new TechHandler()).linkWith(new GeneralHandler());
        assertEquals("billing-ok", root.handle("bill:123"));
        assertEquals("tech-ok", root.handle("tech:wifi"));
        assertEquals("general-ok", root.handle("other"));
    }
}
