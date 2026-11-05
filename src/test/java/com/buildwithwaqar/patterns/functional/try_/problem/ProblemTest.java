package com.buildwithwaqar.patterns.functional.try_.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void success(){ assertEquals(1, Tries.of(()->1).getOrElse(0)); }
@Test void recover(){ assertEquals(9, Tries.of(()->Integer.parseInt("x")).getOrElse(9)); } }
