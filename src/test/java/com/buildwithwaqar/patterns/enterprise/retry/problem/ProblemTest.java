package com.buildwithwaqar.patterns.enterprise.retry.problem;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void eventually(){
    AtomicInteger n=new AtomicInteger();
    assertEquals("ok", Retry.call(3, ()->{ if(n.incrementAndGet()<2) throw new RuntimeException("x"); return "ok"; }));
  }
  @Test void exhausted(){
    assertThrows(RuntimeException.class, ()->Retry.call(2, ()->{ throw new RuntimeException("x"); }));
  }
}
