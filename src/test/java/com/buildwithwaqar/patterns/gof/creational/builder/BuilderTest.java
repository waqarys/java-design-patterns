package com.buildwithwaqar.patterns.gof.creational.builder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {
    @Test void fluentBuilderBuildsImmutableRequest() {
        var req = HttpRequest.builder("https://api.example/v1")
                .method("POST")
                .header("Authorization", "Bearer x")
                .body("{\"a\":1}")
                .timeoutMs(2_000)
                .build();
        assertEquals("POST", req.method());
        assertEquals("Bearer x", req.headers().get("Authorization"));
        assertThrows(UnsupportedOperationException.class, () -> req.headers().put("x", "y"));
    }

    @Test void recordWithers() {
        var call = ApiCall.get("https://x").withBody("hi").withTimeout(100);
        assertEquals("GET", call.method());
        assertEquals("hi", call.body());
        assertEquals(100, call.timeoutMs());
        assertThrows(IllegalArgumentException.class, () -> call.withTimeout(0));
    }
}
