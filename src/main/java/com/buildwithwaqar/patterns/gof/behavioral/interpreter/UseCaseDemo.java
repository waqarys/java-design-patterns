package com.buildwithwaqar.patterns.gof.behavioral.interpreter;

import com.buildwithwaqar.patterns.gof.behavioral.interpreter.*;
import java.util.Map;
/** Problem: evaluate a formula AST from a rules engine. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Interpreter problem: evaluate (2+3)*4 ===");
    Expression e = new MulExpr(new AddExpr(new NumberExpr(2), new NumberExpr(3)), new NumberExpr(4));
    System.out.println("value=" + e.evaluate(Map.of()));
  }
}
