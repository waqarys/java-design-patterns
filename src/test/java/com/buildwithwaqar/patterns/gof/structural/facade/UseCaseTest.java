package com.buildwithwaqar.patterns.gof.structural.facade;
import com.buildwithwaqar.patterns.gof.structural.facade.OrderFacade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void happyAndReject() {
    var f = new OrderFacade();
    assertTrue(f.placeOrder("s", 1, "c", 1).startsWith("OK:"));
    assertEquals("REJECTED:inventory", f.placeOrder("", 1, "c", 1));
  }
}
