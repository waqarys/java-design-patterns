package com.buildwithwaqar.patterns.functional.state;

import com.buildwithwaqar.patterns.functional.state.State;
/** Problem: simulate a bank balance purely (state threaded, not mutated globally). */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== State problem: pure bank simulation ===");
    State<Integer, Integer> deposit20 = State.<Integer>get()
      .flatMap(bal -> State.<Integer>set(bal + 20).flatMap(x -> State.pure(bal + 20)));
    var r = deposit20.run(100);
    System.out.println("returned=" + r.value() + " newBalance=" + r.state());
  }
}
