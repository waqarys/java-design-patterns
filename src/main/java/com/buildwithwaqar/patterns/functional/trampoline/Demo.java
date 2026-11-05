package com.buildwithwaqar.patterns.functional.trampoline;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Trampoline walkthrough ===");
    System.out.println("sum100000=" + Trampoline.safeSum(100_000, 0).run());
  }
}
