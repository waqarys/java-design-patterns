package com.buildwithwaqar.patterns.functional.try_;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZazrTryDemoTest {
    @Test void recovers() {
        assertEquals(80, ZazrTryDemo.portOrDefault("80", 8080));
        assertEquals(8080, ZazrTryDemo.portOrDefault("nope", 8080));
    }
}
