package com.buildwithwaqar.patterns.concurrency.virtualthreads;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VirtualThreadTest {
    @Test void runsMany() throws Exception {
        assertEquals(100, VirtualThreadDemo.fanOut(100));
    }
}
