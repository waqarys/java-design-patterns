package com.buildwithwaqar.patterns.gof.structural.proxy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {
    @Test void loadsLazily() {
        var proxy = new LazyImageProxy("/img.png");
        assertFalse(proxy.loaded());
        assertEquals("showing:/img.png", proxy.display());
        assertTrue(proxy.loaded());
    }
}
