package com.buildwithwaqar.patterns.gof.creational.builder.problem;

import com.buildwithwaqar.patterns.gof.creational.builder.HttpRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuilderProblemTest {
    @Test void buildsImmutableRequest() {
        var req = HttpRequest.builder("https://x").method("PUT").timeoutMs(1).build();
        assertEquals("PUT", req.method());
        assertThrows(UnsupportedOperationException.class, () -> req.headers().put("a", "b"));
    }

    @Test void rejectsBadTimeout() {
        assertThrows(IllegalArgumentException.class,
                () -> HttpRequest.builder("https://x").timeoutMs(0).build());
    }
}
