package com.buildwithwaqar.patterns.concurrency.activeobject;

import com.buildwithwaqar.patterns.concurrency.activeobject.ActiveObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void serializes() throws Exception {
    try (var ao = new ActiveObject()) { ao.add(1).get(); ao.add(2).get(); assertEquals(3, ao.get().get()); }
  }
}
