package com.buildwithwaqar.patterns.gof.behavioral.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {
    @Test void swapsAlgorithms() {
        assertEquals(900, new Cart(PricingStrategy.percentageOff(10)).checkout(1000));
        assertEquals(800, new Cart(PricingStrategy.flatOff(200)).checkout(1000));
        PricingStrategy half = base -> base / 2;
        assertEquals(500, new Cart(half).checkout(1000));
    }
}
