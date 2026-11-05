package com.buildwithwaqar.patterns.enterprise.circuitbreaker.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void opens(){ var cb=new CircuitBreaker(1, 1000); assertThrows(RuntimeException.class, ()->cb.call(()->{throw new RuntimeException("x");})); assertEquals(CbState.OPEN, cb.state()); assertThrows(IllegalStateException.class, ()->cb.call(()->"ok")); } }
