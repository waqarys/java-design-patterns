package com.buildwithwaqar.patterns.functional.try_;

import com.buildwithwaqar.patterns.functional.try_.ZazrTryDemo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void recovers() { assertEquals(8080, ZazrTryDemo.portOrDefault("x", 8080)); }
}
