package com.buildwithwaqar.patterns.functional.validation.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void ok(){ assertTrue(Signup.form("a@b.c",20).isValid()); }
@Test void accumulates(){ var v=Signup.form("bad",10); assertFalse(v.isValid()); assertEquals(2, v.errors().size()); } }
