package com.buildwithwaqar.patterns.concurrency.virtualthreads.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void fanout() throws Exception { assertEquals(50, new UrlChecker().checkAll(50)); } }
