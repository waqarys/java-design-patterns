package com.buildwithwaqar.patterns.functional.writer;

import com.buildwithwaqar.patterns.functional.writer.Writer;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void audits() {
    var m = Monoids.STRING;
    var w = Writer.<String, Integer>pure(m, 1).flatMap(m, n -> new Writer<>("x;", n+1));
    assertEquals(2, w.value());
    assertEquals("x;", w.log());
  }
}
