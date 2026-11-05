package com.buildwithwaqar.patterns.functional.monoid;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MonoidTest {
    @Test void folds() {
        assertEquals(6, Monoids.INT_SUM.fold(List.of(1, 2, 3)));
        assertEquals("abc", Monoids.STRING.fold(List.of("a", "b", "c")));
    }
}
