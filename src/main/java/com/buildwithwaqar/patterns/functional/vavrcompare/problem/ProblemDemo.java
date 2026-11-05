package com.buildwithwaqar.patterns.functional.vavrcompare.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== VAVR vs Zazr: same Option describe ===");
    System.out.println("--- naive ---"); System.out.println(NaiveOpt.describe(null));
System.out.println("--- compare ---");
System.out.println(OptionCompare.zazrDescribe("x"));
System.out.println(OptionCompare.vavrStyleDescribe("x"));
System.out.println(OptionCompare.zazrDescribe(null));
System.out.println(OptionCompare.vavrStyleDescribe(null));
  }
}
