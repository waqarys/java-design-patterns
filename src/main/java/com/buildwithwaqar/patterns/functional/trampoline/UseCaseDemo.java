package com.buildwithwaqar.patterns.functional.trampoline;

import com.buildwithwaqar.patterns.functional.trampoline.Trampoline;
/** Problem: sum 1..N recursively without StackOverflowError. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Trampoline problem: deep recursion ===");
    System.out.println("sum100000=" + Trampoline.safeSum(100_000, 0).run());
  }
}
