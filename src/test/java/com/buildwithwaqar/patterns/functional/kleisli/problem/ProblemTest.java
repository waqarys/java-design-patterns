package com.buildwithwaqar.patterns.functional.kleisli.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void ok(){ assertEquals(Optional.of("allow:1007"), AuthPipe.pipeline.apply("7")); }
@Test void fail(){ assertTrue(AuthPipe.pipeline.apply("x").isEmpty()); } }
