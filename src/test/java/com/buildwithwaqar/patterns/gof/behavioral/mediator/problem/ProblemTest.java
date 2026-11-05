package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void delivers(){ var r=new ChatRoom(); List<String> b=new ArrayList<>(); r.join("A",m->{}); r.join("B",b::add); r.send("A","B","x"); assertEquals(List.of("A->B:x"), b); }
@Test void missingFails(){ var r=new ChatRoom(); r.join("A",m->{}); assertThrows(IllegalArgumentException.class, ()->r.send("A","Z","x")); }
}
