package com.buildwithwaqar.patterns.functional.state.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void deposit(){ assertEquals(75, State.deposit(50).flatMap(b->State.deposit(25)).run(0).getKey()); } }
