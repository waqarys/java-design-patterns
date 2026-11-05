package com.buildwithwaqar.patterns.enterprise.nullobject;

import com.buildwithwaqar.patterns.enterprise.nullobject.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void nullSafe() { assertEquals("ok:1", new OrderService(null).place("1")); }
}
