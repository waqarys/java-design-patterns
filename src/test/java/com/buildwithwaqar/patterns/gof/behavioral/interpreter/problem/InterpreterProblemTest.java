package com.buildwithwaqar.patterns.gof.behavioral.interpreter.problem;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class InterpreterProblemTest {
    @Test void percentThenFlat() {
        var rule = new PricingDsl.FlatOff(new PricingDsl.PercentOff(new PricingDsl.Base("SKU"), 10), 200);
        assertEquals(1600, rule.apply(Map.of("SKU", 2000)));
    }
    @Test void unknownSkuFails() {
        assertThrows(IllegalArgumentException.class, () -> new PricingDsl.Base("X").apply(Map.of()));
    }
    @Test void badPercentFails() {
        assertThrows(IllegalArgumentException.class,
                () -> new PricingDsl.PercentOff(new PricingDsl.Base("SKU"), 150).apply(Map.of("SKU", 1)));
    }
}
