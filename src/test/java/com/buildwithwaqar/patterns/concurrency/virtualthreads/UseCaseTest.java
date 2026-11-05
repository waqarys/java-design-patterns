package com.buildwithwaqar.patterns.concurrency.virtualthreads;

import com.buildwithwaqar.patterns.concurrency.virtualthreads.VirtualThreadDemo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void fans() throws Exception { assertEquals(20, VirtualThreadDemo.fanOut(20)); }
}
