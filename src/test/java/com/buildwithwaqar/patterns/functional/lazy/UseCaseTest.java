package com.buildwithwaqar.patterns.functional.lazy;

import com.buildwithwaqar.patterns.functional.lazy.LazyDemo;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void once() {
    var h = new AtomicInteger();
    var l = LazyDemo.expensive(h);
    l.get(); l.get();
    assertEquals(1, h.get());
  }
}
