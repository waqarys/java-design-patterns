package com.buildwithwaqar.patterns.gof.behavioral.state;

import com.buildwithwaqar.patterns.gof.behavioral.state.OrderStateMachine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void happyAndIllegal() {
    var o = new OrderStateMachine(); o.pay(); o.ship();
    assertEquals("SHIPPED", o.status());
    assertThrows(IllegalStateException.class, o::cancel);
  }
}
