package com.buildwithwaqar.patterns.functional.either.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void ok(){ assertTrue(UserPipeline.run("7").isRight()); }
@Test void parseFail(){ assertEquals("parse", UserPipeline.run("x").left()); }
@Test void positiveFail(){ assertEquals("positive", UserPipeline.run("-3").left()); } }
