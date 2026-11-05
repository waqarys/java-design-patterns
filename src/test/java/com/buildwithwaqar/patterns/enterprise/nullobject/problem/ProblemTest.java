package com.buildwithwaqar.patterns.enterprise.nullobject.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void nullObjectSafe(){ new OrderService(new NullLogger()).place("x"); }
@Test void consoleRecords(){ var c=new ConsoleLogger(); new OrderService(c).place("o"); assertEquals(List.of("placed o"), c.lines()); } }
