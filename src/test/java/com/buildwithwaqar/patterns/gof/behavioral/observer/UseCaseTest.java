package com.buildwithwaqar.patterns.gof.behavioral.observer;

import com.buildwithwaqar.patterns.gof.behavioral.observer.EventBus;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void notifies() throws Exception {
    var bus = new EventBus<String>(); var seen = new ArrayList<String>();
    try (var s = bus.subscribe(seen::add)) { bus.publish("x"); }
    assertEquals(java.util.List.of("x"), seen);
  }
}
