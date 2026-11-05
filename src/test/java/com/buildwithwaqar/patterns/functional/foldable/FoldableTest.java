package com.buildwithwaqar.patterns.functional.foldable;

import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FoldableTest {
    @Test void foldsWithMonoid() {
        assertEquals(6, Foldable.fold(List.of(1, 2, 3), Monoids.INT_SUM));
    }
}
