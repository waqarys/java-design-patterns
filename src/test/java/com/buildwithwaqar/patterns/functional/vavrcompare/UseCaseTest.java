package com.buildwithwaqar.patterns.functional.vavrcompare;

import com.buildwithwaqar.patterns.functional.vavrcompare.VavrVsZazr;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void both() {
    assertEquals("got 1", VavrVsZazr.vavrDescribe(io.vavr.control.Option.some(1)));
    assertEquals("got 1", VavrVsZazr.zazrDescribe(dev.zazr.control.Option.some(1)));
  }
}
