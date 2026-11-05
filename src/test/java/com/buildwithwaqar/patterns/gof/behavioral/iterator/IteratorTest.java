package com.buildwithwaqar.patterns.gof.behavioral.iterator;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IteratorTest {
    @Test void cycles() {
        var it = new RoundRobin<>(List.of("a", "b")).iterator();
        assertEquals("a", it.next());
        assertEquals("b", it.next());
        assertEquals("a", it.next());
    }
}
