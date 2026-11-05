package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void cycles(){ var it=new RoundRobin(List.of("a","b")).iterator(); assertEquals("a",it.next()); assertEquals("b",it.next()); assertEquals("a",it.next()); }
@Test void emptyFails(){ assertThrows(NoSuchElementException.class, () -> new RoundRobin(List.of()).iterator().next()); }
}
