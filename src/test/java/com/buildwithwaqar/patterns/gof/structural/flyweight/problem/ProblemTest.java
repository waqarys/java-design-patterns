package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void sharesStyles(){ var f=new GlyphFactory(); f.style("A",1); f.style("A",1); assertEquals(1,f.cached()); }
}
