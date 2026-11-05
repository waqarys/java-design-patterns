package com.buildwithwaqar.patterns.functional.state;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== State walkthrough ===");
    State<Integer, Integer> program = State.<Integer>get()
      .flatMap(n -> State.<Integer>set(n + 1).flatMap(x -> State.pure(n)));
    var r = program.run(10);
    System.out.println("value=" + r.value() + " state=" + r.state());
  }
}
