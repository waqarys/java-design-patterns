package com.buildwithwaqar.patterns.enterprise.di.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void wires(){ var c=new DiContainer(); c.register(Greeter.class, new FormalGreeter()); assertEquals("Hello, Ada", new HelloController(c.get(Greeter.class)).hello("Ada")); }
@Test void missingFails(){ assertThrows(IllegalStateException.class, () -> new DiContainer().get(Greeter.class)); } }
