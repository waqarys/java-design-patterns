package com.buildwithwaqar.patterns.gof.behavioral.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test void happyPath() {
        var o = new OrderStateMachine();
        o.pay(); o.ship();
        assertEquals("SHIPPED", o.status());
    }

    @Test void illegalTransition() {
        var o = new OrderStateMachine();
        assertThrows(IllegalStateException.class, o::ship);
    }
}
