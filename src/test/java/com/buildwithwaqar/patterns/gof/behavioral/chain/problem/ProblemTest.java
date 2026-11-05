package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void routes(){ var h=new BillingHandler(); h.link(new TechHandler()).link(new GeneralHandler());
  assertEquals("billing-desk", h.handle("billing")); assertEquals("general-desk", h.handle("x")); }
}
