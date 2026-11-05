package com.buildwithwaqar.patterns.gof.structural.bridge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void mixesIndependently() {
    assertEquals("vector:circle(1)", BridgeShapes.vectorCircle(1));
    assertEquals("raster:square(2)", BridgeShapes.rasterSquare(2));
  }
}
