package com.buildwithwaqar.patterns.functional.vavrcompare;

import com.buildwithwaqar.patterns.functional.vavrcompare.VavrVsZazr;
/** Problem: migrate Option describe from VAVR to Zazr pattern matching. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== VAVR vs Zazr problem ===");
    System.out.println("vavr=" + VavrVsZazr.vavrDescribe(io.vavr.control.Option.some(7)));
    System.out.println("zazr=" + VavrVsZazr.zazrDescribe(dev.zazr.control.Option.some(7)));
  }
}
