package com.buildwithwaqar.patterns.gof.behavioral.interpreter;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    @Test void evaluatesTree() {
        Expression expr = new MulExpr(new AddExpr(new NumberExpr(2), new NumberExpr(3)), new NumberExpr(4));
        assertEquals(20, expr.evaluate(Map.of()));
    }
}
