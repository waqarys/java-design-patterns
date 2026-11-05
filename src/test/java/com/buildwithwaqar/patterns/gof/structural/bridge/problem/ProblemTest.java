package com.buildwithwaqar.patterns.gof.structural.bridge.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void independentAxes(){
  assertEquals("vector-circle r=3", new Circle(new VectorRenderer(),3).draw());
  assertEquals("raster-square s=4", new Square(new RasterRenderer(),4).draw());
}
}
