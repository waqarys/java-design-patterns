package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void transfers() throws Exception { var q=new BoundedQueue<String>(1); q.put("x"); assertEquals("x", q.take()); } }
