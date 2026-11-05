package com.buildwithwaqar.patterns.functional.foldable.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void traverseFail(){ assertTrue(Foldables.traverse(List.of("a"), s->Optional.empty()).isEmpty()); }
@Test void fold(){ assertEquals(6, Foldables.foldLeft(List.of(1,2,3),0,Integer::sum)); } }
