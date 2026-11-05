package com.buildwithwaqar.patterns.functional.trampoline.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Trampoline: deep sum without SOE ===");
    System.out.println("--- naive (may SOE for large n) ---");
System.out.println(NaiveSum.sum(100));
System.out.println("--- trampoline ---");
System.out.println(SafeSum.sum(100000, 0).run());
  }
}
