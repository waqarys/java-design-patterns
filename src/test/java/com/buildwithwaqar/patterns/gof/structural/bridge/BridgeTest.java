package com.buildwithwaqar.patterns.gof.structural.bridge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BridgeTest {
    @Test void mixesAbstractionsAndImplementations() {
        assertEquals("vector:circle(3)", new Circle(new VectorRenderer(), 3).draw());
        assertEquals("raster:square(4)", new Square(new RasterRenderer(), 4).draw());
    }
}
