package com.buildwithwaqar.patterns.functional.functions.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void pipeline(){ assertEquals(850, Fns.pricePipeline(10,50).apply(1000)); }
@Test void curry(){ assertEquals(5, Fns.curryAdd().apply(2).apply(3)); } }
