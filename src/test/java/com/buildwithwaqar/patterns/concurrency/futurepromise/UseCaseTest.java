package com.buildwithwaqar.patterns.concurrency.futurepromise;

import com.buildwithwaqar.patterns.concurrency.futurepromise.Promise;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void doubles() throws Exception { assertEquals(10, Promise.computeAsync(5).toCompletableFuture().get()); }
}
