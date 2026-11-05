package com.buildwithwaqar.patterns.concurrency.activeobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActiveObjectTest {
    @Test void serializesCalls() throws Exception {
        try (var ao = new ActiveObject()) {
            ao.add(2).get();
            ao.add(3).get();
            assertEquals(5, ao.get().get());
        }
    }
}
