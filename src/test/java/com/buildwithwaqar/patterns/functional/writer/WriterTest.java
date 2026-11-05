package com.buildwithwaqar.patterns.functional.writer;

import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WriterTest {
    @Test void accumulatesLog() {
        var m = Monoids.STRING;
        Writer<String, Integer> w = Writer.<String, Integer>pure(m, 1)
                .flatMap(m, n -> new Writer<>("step1;", n + 1))
                .flatMap(m, n -> new Writer<>("step2;", n * 2));
        assertEquals(4, w.value());
        assertEquals("step1;step2;", w.log());
    }
}
