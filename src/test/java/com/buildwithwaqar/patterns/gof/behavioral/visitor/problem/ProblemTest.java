package com.buildwithwaqar.patterns.gof.behavioral.visitor.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void eval(){ Expr e=new Mul(new Add(new Num(2),new Num(3)), new Num(4)); assertEquals(20, e.accept(new Eval())); assertEquals("((2+3)*4)", e.accept(new Pretty())); }
}
