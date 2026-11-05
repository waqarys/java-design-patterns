package com.buildwithwaqar.patterns.functional.io;

import com.buildwithwaqar.patterns.functional.io.IO;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void defers() {
    var h = new AtomicInteger();
    var io = IO.delay(h::incrementAndGet);
    assertEquals(0, h.get());
    io.unsafeRun();
    assertEquals(1, h.get());
  }
}
