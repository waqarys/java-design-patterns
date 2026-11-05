package com.buildwithwaqar.patterns.gof.behavioral.visitor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {
    @Test void patternMatchingMatchesVisitor() {
        Element c = new CircleEl(2);
        assertEquals(ShapeVisitor.area(c), ShapeVisitor.areaViaVisitor(c), 1e-9);
        assertEquals(6.0, ShapeVisitor.area(new RectEl(2, 3)), 1e-9);
    }
}
