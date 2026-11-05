package com.buildwithwaqar.patterns.functional.option.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Option: parse positive port ===");
    System.out.println("--- naive ---"); System.out.println(NaivePort.parse("8080")); System.out.println(NaivePort.parse("-1"));
System.out.println("--- option ---");
System.out.println(Port.describe(Port.parse("8080")));
System.out.println(Port.describe(Port.parse("nope")));
System.out.println(Port.describe(Port.parse("0")));
  }
}
