package com.buildwithwaqar.patterns.functional.state.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== State: pure RNG / bank balance ===");
    System.out.println("--- naive ---"); var n=new NaiveRng(1); System.out.println(n.next()+" "+n.next());
System.out.println("--- state RNG ---");
var rng=State.nextInt().flatMap(a -> State.nextInt().map(b -> List.of(a,b)));
System.out.println(rng.run(1));
System.out.println("--- state bank ---");
System.out.println(State.deposit(50).flatMap(b -> State.deposit(25)).run(0));
  }
}
