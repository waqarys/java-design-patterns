package com.buildwithwaqar.patterns.functional.lazy.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void memoizes(){ java.util.concurrent.atomic.AtomicInteger n=new java.util.concurrent.atomic.AtomicInteger(); var l=Lazy.of(n::incrementAndGet); l.get(); l.get(); assertEquals(1,n.get()); } }
