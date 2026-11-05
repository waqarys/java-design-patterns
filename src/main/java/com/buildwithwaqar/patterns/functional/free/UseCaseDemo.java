package com.buildwithwaqar.patterns.functional.free;

import com.buildwithwaqar.patterns.functional.free.Free;
/** Problem: write a console script once; interpret with canned input in tests. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Free Monad problem: interpretable console script ===");
    Free<String> program = Free.printLine("name?").flatMap(x -> Free.readLine()).map(n -> "hi " + n);
    var out = new StringBuilder();
    System.out.println("result=" + Free.interpret(program, "Waqar", out));
    System.out.println("console=" + out.toString().trim());
  }
}
