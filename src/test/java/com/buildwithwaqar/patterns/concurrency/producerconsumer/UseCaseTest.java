package com.buildwithwaqar.patterns.concurrency.producerconsumer;

import com.buildwithwaqar.patterns.concurrency.producerconsumer.BoundedQueue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void transfers() throws Exception {
    var q = new BoundedQueue<Integer>(1);
    q.put(7); assertEquals(7, q.take());
  }
}
