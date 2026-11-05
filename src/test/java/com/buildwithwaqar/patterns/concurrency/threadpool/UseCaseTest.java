package com.buildwithwaqar.patterns.concurrency.threadpool;

import com.buildwithwaqar.patterns.concurrency.threadpool.SimpleThreadPool;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void runs() throws Exception {
    var n = new AtomicInteger();
    try (var p = new SimpleThreadPool(2)) { for (int i=0;i<5;i++) p.submit(n::incrementAndGet); Thread.sleep(200); }
    assertEquals(5, n.get());
  }
}
