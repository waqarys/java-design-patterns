package com.buildwithwaqar.patterns.functional.vavrcompare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VavrVsZazrTest {
    @Test void bothStyles() {
        assertEquals("got 1", VavrVsZazr.vavrDescribe(io.vavr.control.Option.some(1)));
        assertEquals("missing", VavrVsZazr.vavrDescribe(io.vavr.control.Option.none()));
        assertEquals("got 1", VavrVsZazr.zazrDescribe(dev.zazr.control.Option.some(1)));
        assertEquals("missing", VavrVsZazr.zazrDescribe(dev.zazr.control.Option.none()));
    }
}
