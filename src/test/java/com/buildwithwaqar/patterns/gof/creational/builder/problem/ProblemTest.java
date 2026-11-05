package com.buildwithwaqar.patterns.gof.creational.builder.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void builds(){ assertTrue(new HttpRequest.Builder().url("/a").build().summarize().contains("/a")); }
@Test void requiresUrl(){ assertThrows(IllegalArgumentException.class, () -> new HttpRequest.Builder().build()); }
}
