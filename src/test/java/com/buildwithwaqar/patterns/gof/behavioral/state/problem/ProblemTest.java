package com.buildwithwaqar.patterns.gof.behavioral.state.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void happy(){ var o=new Order(); o.pay(); o.ship(); assertEquals(Phase.SHIPPED, o.phase()); }
@Test void illegalShipFails(){ assertThrows(IllegalStateException.class, () -> new Order().ship()); }
}
