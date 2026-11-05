package com.buildwithwaqar.patterns.functional.monoid.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void fold(){ assertEquals(60, Monoids.fold(Monoids.INT_ADD, List.of(10,20,30))); assertEquals("", Monoids.fold(Monoids.STRING, List.of())); } }
