package com.buildwithwaqar.patterns.functional.functions;

import com.buildwithwaqar.patterns.functional.functions.Fn;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void memo() {
    var h = new AtomicInteger();
    var m = Fn.memoize((Integer x) -> { h.incrementAndGet(); return x; });
    m.apply(1); m.apply(1);
    assertEquals(1, h.get());
  }
}
