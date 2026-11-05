package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void restore(){ var d=new Doc(); var h=new History(); d.write("x"); h.push(d.save()); d.write("y"); d.restore(h.pop()); assertEquals("x", d.text()); }
@Test void emptyHistoryFails(){ assertThrows(IllegalStateException.class, () -> new History().pop()); }
}
