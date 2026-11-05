package com.buildwithwaqar.patterns.gof.structural.proxy;
import com.buildwithwaqar.patterns.gof.structural.proxy.LazyImageProxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void loadsLazily() {
    var p = new LazyImageProxy("/x");
    assertFalse(p.loaded());
    p.display();
    assertTrue(p.loaded());
  }
}
