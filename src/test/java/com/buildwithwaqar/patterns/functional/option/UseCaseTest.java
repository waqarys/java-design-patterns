package com.buildwithwaqar.patterns.functional.option;

import com.buildwithwaqar.patterns.functional.option.ZazrOptionDemo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void parses() { assertEquals("got 1", ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("1"))); }
}
