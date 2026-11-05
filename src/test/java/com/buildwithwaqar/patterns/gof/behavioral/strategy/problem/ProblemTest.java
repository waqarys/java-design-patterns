package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void strategies(){ assertEquals(900, new Cart(new PercentOff(10)).total(1000)); assertEquals(800, new Cart(new FlatOff(200)).total(1000)); }
}
