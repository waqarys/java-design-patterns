package com.buildwithwaqar.patterns.functional.either;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RailwayTest {
    @Test void happyPath() {
        assertEquals("ok:id-7", Railway.explain(Railway.label("7")));
    }

    @Test void shortCircuits() {
        assertTrue(Railway.explain(Railway.label("x")).startsWith("err:"));
        assertTrue(Railway.explain(Railway.label("-3")).startsWith("err:"));
    }
}
