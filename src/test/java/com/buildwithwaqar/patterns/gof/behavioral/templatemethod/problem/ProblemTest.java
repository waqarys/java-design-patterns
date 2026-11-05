package com.buildwithwaqar.patterns.gof.behavioral.templatemethod.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void csv(){ assertEquals("rows=2", new CsvMiner().mine("a\nb")); }
}
