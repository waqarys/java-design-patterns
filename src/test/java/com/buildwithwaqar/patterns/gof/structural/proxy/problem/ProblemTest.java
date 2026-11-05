package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void lazy(){ Image p=new LazyImageProxy("/x"); assertFalse(p.loaded()); p.display(); assertTrue(p.loaded()); }
}
