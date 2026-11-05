package com.buildwithwaqar.patterns.functional.functions;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class FnTest {
    @Test void curryComposeMemo() {
        var add = Fn.curry((Integer a, Integer b) -> a + b);
        assertEquals(5, add.apply(2).apply(3));
        var pipeline = Fn.compose((Integer x) -> x * 2, (Integer x) -> x + 1);
        assertEquals(8, pipeline.apply(3));
        var hits = new AtomicInteger();
        var memo = Fn.memoize((Integer x) -> { hits.incrementAndGet(); return x * x; });
        assertEquals(9, memo.apply(3));
        assertEquals(9, memo.apply(3));
        assertEquals(1, hits.get());
    }
}
