package com.buildwithwaqar.patterns.gof.behavioral.observer;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {
    @Test void notifiesSubscribers() throws Exception {
        var bus = new EventBus<String>();
        var seen = new ArrayList<String>();
        try (var sub = bus.subscribe(seen::add)) {
            bus.publish("ping");
        }
        bus.publish("pong");
        assertEquals(List.of("ping"), seen);
    }
}
