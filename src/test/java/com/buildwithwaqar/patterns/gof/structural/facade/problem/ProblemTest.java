package com.buildwithwaqar.patterns.gof.structural.facade.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void ok(){ assertEquals("OK:SKU", new OrderFacade().placeOrder("SKU",1,"A")); }
@Test void paymentFail(){ assertEquals("FAIL:payment", new OrderFacade().placeOrder("SKU",0,"A")); }
}
