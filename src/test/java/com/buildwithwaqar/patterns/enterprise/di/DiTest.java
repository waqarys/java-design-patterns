package com.buildwithwaqar.patterns.enterprise.di;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiTest {
    @Test void resolvesSingleton() {
        var c = new DiContainer();
        c.singleton(Greeter.class, FriendlyGreeter::new);
        assertSame(c.get(Greeter.class), c.get(Greeter.class));
        assertEquals("hello waqar", c.get(Greeter.class).greet("waqar"));
    }
}
