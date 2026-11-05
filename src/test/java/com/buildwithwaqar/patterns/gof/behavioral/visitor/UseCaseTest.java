package com.buildwithwaqar.patterns.gof.behavioral.visitor;

import com.buildwithwaqar.patterns.gof.behavioral.visitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void areasMatch() {
    Element c = new CircleEl(2);
    assertEquals(ShapeVisitor.area(c), ShapeVisitor.areaViaVisitor(c), 1e-9);
  }
}
