package com.buildwithwaqar.patterns.gof.behavioral.interpreter;

import com.buildwithwaqar.patterns.gof.behavioral.interpreter.*;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void eval() {
    assertEquals(20, new MulExpr(new AddExpr(new NumberExpr(2), new NumberExpr(3)), new NumberExpr(4)).evaluate(Map.of()));
  }
}
