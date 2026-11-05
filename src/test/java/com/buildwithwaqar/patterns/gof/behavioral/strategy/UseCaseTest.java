package com.buildwithwaqar.patterns.gof.behavioral.strategy;

import com.buildwithwaqar.patterns.gof.behavioral.strategy.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void swaps() {
    assertEquals(900, new Cart(PricingStrategy.percentageOff(10)).checkout(1000));
  }
}
