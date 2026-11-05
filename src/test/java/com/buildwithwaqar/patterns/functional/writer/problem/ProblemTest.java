package com.buildwithwaqar.patterns.functional.writer.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void logs(){ var w=Audited.total(List.of(1,2)); assertEquals(3, w.value()); assertEquals(2, w.log().size()); } }
