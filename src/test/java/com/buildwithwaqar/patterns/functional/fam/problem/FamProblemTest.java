package com.buildwithwaqar.patterns.functional.fam.problem;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import static org.junit.jupiter.api.Assertions.*;

class FamProblemTest {
    @Test void optionalMap2RequiresBoth() {
        assertTrue(AsyncDashboard.dashboardOpt(
                Optional.of(new AsyncDashboard.User("1", "A")),
                Optional.of(new AsyncDashboard.Order("o", "1", 1))).isPresent());
        assertTrue(AsyncDashboard.dashboardOpt(
                Optional.of(new AsyncDashboard.User("1", "A")), Optional.empty()).isEmpty());
    }

    @Test void asyncCombines() {
        var d = AsyncDashboard.dashboardAsync(
                AsyncDashboard.fetchUser("1", true),
                AsyncDashboard.fetchOrder("o", "1", true)).join();
        assertEquals("User-1", d.name());
        assertEquals(4200, d.totalCents());
    }

    @Test void asyncFailurePropagates() {
        assertThrows(CompletionException.class, () ->
                AsyncDashboard.dashboardAsync(
                        AsyncDashboard.fetchUser("1", false),
                        AsyncDashboard.fetchOrder("o", "1", true)).join());
    }
}
