package com.buildwithwaqar.patterns.functional.functions.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Currying/Composition: price pipeline ===");
    System.out.println("--- naive ---"); System.out.println(NaivePrice.price(1000,10,50));
System.out.println("--- curry/compose ---");
System.out.println(Fns.curryAdd().apply(2).apply(3));
System.out.println(Fns.pricePipeline(10,50).apply(1000));
  }
}
