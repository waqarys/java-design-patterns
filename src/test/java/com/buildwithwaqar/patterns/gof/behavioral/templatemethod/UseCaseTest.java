package com.buildwithwaqar.patterns.gof.behavioral.templatemethod;

import com.buildwithwaqar.patterns.gof.behavioral.templatemethod.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void mines() { assertEquals("rows=2", new CsvMiner().mine("x")); }
}
