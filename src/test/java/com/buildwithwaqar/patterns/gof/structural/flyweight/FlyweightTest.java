package com.buildwithwaqar.patterns.gof.structural.flyweight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlyweightTest {
    @Test void reusesIntrinsicState() {
        var factory = new GlyphFactory();
        var a = factory.style("Inter", 14, "#fff");
        var b = factory.style("Inter", 14, "#fff");
        assertSame(a, b);
        assertEquals(1, factory.pooled());
        var g = factory.glyph('A', a, 0, 0);
        assertEquals('A', g.character());
    }
}
