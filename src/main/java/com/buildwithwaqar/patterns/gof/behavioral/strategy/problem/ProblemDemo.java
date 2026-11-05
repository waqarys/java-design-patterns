package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Strategy: cart pricing ===");
    System.out.println("--- naive ---"); System.out.println(NaiveCart.total(1000,"pct"));
System.out.println("--- strategy ---");
System.out.println(new Cart(new PercentOff(10)).total(1000));
System.out.println(new Cart(new FlatOff(200)).total(1000));
System.out.println(new Cart(new HalfOff()).total(1000));
  }
}
