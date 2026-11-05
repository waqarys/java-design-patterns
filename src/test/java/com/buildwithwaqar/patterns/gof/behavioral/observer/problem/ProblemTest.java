package com.buildwithwaqar.patterns.gof.behavioral.observer.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void fansOut(){ var bus=new EventBus(); List<String> log=new ArrayList<>(); bus.on("e",(ev,id)->log.add(id)); bus.publish("e","1"); bus.publish("e","2"); assertEquals(List.of("1","2"), log); }
@Test void noSubsIsNoop(){ new EventBus().publish("x","1"); }
}
