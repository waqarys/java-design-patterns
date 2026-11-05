package com.buildwithwaqar.patterns.functional.cake;

import com.buildwithwaqar.patterns.functional.cake.CakePattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void wires() {
    assertTrue(new CakePattern.App().userService().welcome("1").contains("1@example.com"));
  }
}
