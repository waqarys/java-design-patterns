package com.buildwithwaqar.patterns.functional.trampoline;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrampolineTest {
    @Test void doesNotBlowStack() {
        assertEquals(5_000_050_000L, Trampoline.safeSum(100_000, 0).run());
    }
}
