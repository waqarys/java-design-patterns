package com.buildwithwaqar.patterns.functional.trampoline.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void deep(){ assertEquals(5000050000L, SafeSum.sum(100000,0).run()); }
@Test void small(){ assertEquals(55L, SafeSum.sum(10,0).run()); } }
