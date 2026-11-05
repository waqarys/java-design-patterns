package com.buildwithwaqar.patterns.concurrency.readwritelock;

import com.buildwithwaqar.patterns.concurrency.readwritelock.CachedValue;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void loadsOnce() {
    var n = new AtomicInteger();
    var c = new CachedValue<String>();
    c.get(() -> { n.incrementAndGet(); return "x"; });
    c.get(() -> { n.incrementAndGet(); return "y"; });
    assertEquals(1, n.get());
  }
}
