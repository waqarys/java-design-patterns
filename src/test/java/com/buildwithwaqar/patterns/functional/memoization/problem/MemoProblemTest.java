package com.buildwithwaqar.patterns.functional.memoization.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoProblemTest {
    @Test void memoMatchesNaiveWithFewerCalls() {
        int[] coins = {1, 5, 10};
        var n = new CoinChangeMemo.Counter();
        var m = new CoinChangeMemo.Counter();
        assertEquals(CoinChangeMemo.naiveWays(20, coins, n), CoinChangeMemo.memoWays(20, coins, m));
        assertTrue(m.get() < n.get());
    }

    @Test void taxMemoizeComputesOncePerKey() {
        var c = new CoinChangeMemo.Counter();
        var tax = CoinChangeMemo.memoize(r -> r.equals("CA") ? 725 : 500, c);
        tax.apply("CA"); tax.apply("CA"); tax.apply("NY");
        assertEquals(2, c.get());
    }
}
