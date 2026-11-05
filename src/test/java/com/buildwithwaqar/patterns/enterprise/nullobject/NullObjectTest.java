package com.buildwithwaqar.patterns.enterprise.nullobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NullObjectTest {
    @Test void nullLoggerIsSafe() {
        assertEquals("ok:1", new OrderService(null).place("1"));
    }

    @Test void realLoggerRecords() {
        var sink = new StringBuilder();
        new OrderService(new ConsoleLogger(sink)).place("2");
        assertTrue(sink.toString().contains("placing 2"));
    }
}
