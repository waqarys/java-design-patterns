package com.buildwithwaqar.patterns.concurrency.threadpool.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void runs() throws Exception { var p=new SimpleThreadPool(2); assertEquals(4, p.submit(()->2*2).get()); p.shutdown(); } }
