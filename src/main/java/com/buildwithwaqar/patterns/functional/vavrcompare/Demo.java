package com.buildwithwaqar.patterns.functional.vavrcompare;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== VAVR vs Zazr walkthrough ===");
    System.out.println("vavrSome=" + VavrVsZazr.vavrDescribe(io.vavr.control.Option.some(1)));
    System.out.println("vavrNone=" + VavrVsZazr.vavrDescribe(io.vavr.control.Option.none()));
    System.out.println("zazrSome=" + VavrVsZazr.zazrDescribe(dev.zazr.control.Option.some(1)));
    System.out.println("zazrNone=" + VavrVsZazr.zazrDescribe(dev.zazr.control.Option.none()));
  }
}
