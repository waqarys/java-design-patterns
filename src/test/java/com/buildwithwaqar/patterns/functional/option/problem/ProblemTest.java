package com.buildwithwaqar.patterns.functional.option.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void ok(){ assertEquals(8080, Port.parse("8080").getAsInt()); }
@Test void bad(){ assertTrue(Port.parse("x").isEmpty()); assertTrue(Port.parse("0").isEmpty()); } }
