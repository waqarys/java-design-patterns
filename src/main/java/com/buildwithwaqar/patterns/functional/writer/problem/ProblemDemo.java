package com.buildwithwaqar.patterns.functional.writer.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Writer: totals with audit log ===");
    System.out.println("--- naive ---"); System.out.println(NaiveAudit.total(List.of(10,20)));
System.out.println("--- writer ---");
var w=Audited.total(List.of(10,20,30));
System.out.println("value="+w.value()); System.out.println("log="+w.log());
  }
}
