package com.buildwithwaqar.patterns.functional.free;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Free Monad walkthrough ===");
    Free<String> program = Free.printLine("name?")
      .flatMap(ignored -> Free.readLine())
      .map(n -> "hi " + n);
    var out = new StringBuilder();
    System.out.println("result=" + Free.interpret(program, "Waqar", out));
    System.out.println("console=" + out.toString().trim());
  }
}
