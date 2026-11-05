package com.buildwithwaqar.patterns.enterprise.retry;

import com.buildwithwaqar.patterns.enterprise.retry.Retry;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void retries() throws Exception {
    var n = new AtomicInteger();
    assertEquals("ok", new Retry(3, e -> true).execute(() -> {
      if (n.incrementAndGet() < 2) throw new IllegalStateException();
      return "ok";
    }));
  }
}
