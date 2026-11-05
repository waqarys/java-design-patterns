package com.buildwithwaqar.patterns.concurrency.futurepromise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromiseTest {
    @Test void completes() throws Exception {
        assertEquals(10, Promise.computeAsync(5).toCompletableFuture().get());
        assertEquals(6, Promise.asFuture(3).get());
    }
}
