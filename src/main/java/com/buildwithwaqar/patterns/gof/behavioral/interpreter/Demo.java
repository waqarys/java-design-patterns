package com.buildwithwaqar.patterns.gof.behavioral.interpreter;
import java.util.Map;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Interpreter walkthrough ===");
    Expression expr = new MulExpr(new AddExpr(new NumberExpr(2), new NumberExpr(3)), new NumberExpr(4));
    System.out.println("ast=(2+3)*4 value=" + expr.evaluate(Map.of()));
  }
}
