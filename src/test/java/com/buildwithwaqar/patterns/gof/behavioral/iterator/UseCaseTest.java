package com.buildwithwaqar.patterns.gof.behavioral.iterator;

import com.buildwithwaqar.patterns.gof.behavioral.iterator.RoundRobin;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void cycles() {
    var it = new RoundRobin<>(List.of("a","b")).iterator();
    assertEquals("a", it.next());
    assertEquals("b", it.next());
    assertEquals("a", it.next());
  }
}
