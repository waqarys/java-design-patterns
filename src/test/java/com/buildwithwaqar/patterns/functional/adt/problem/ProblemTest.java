package com.buildwithwaqar.patterns.functional.adt.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void exhaust(){ assertTrue(Payments.describe(new Card("1",1)).contains("card")); assertTrue(Payments.describe(new Cash(1)).startsWith("cash")); } }
