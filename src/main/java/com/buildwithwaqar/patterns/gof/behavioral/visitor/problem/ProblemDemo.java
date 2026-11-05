package com.buildwithwaqar.patterns.gof.behavioral.visitor.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Visitor: AST pretty-print / eval ===");
    System.out.println("--- naive ---"); System.out.println(NaiveAst.show("add",2,3));
System.out.println("--- visitor ---");
Expr e=new Mul(new Add(new Num(2), new Num(3)), new Num(4));
System.out.println(e.accept(new Pretty()));
System.out.println("value="+e.accept(new Eval()));
  }
}
