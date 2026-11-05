package com.buildwithwaqar.patterns.gof.behavioral.chain;

import com.buildwithwaqar.patterns.gof.behavioral.chain.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void routes() {
    var root = new BillingHandler();
    root.linkWith(new TechHandler()).linkWith(new GeneralHandler());
    assertEquals("billing-ok", root.handle("bill:1"));
    assertEquals("tech-ok", root.handle("tech:1"));
    assertEquals("general-ok", root.handle("other"));
  }
}
