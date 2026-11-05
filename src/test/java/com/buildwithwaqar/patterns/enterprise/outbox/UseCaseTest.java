package com.buildwithwaqar.patterns.enterprise.outbox;

import com.buildwithwaqar.patterns.enterprise.outbox.Outbox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void relays() {
    var o = new Outbox(); o.placeOrder("1","{}");
    assertEquals(1, o.unpublished().size());
    o.publishDue();
    assertTrue(o.unpublished().isEmpty());
  }
}
