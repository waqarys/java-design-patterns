package com.buildwithwaqar.patterns.functional.lazy.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Lazy/IO: defer expensive report ===");
    System.out.println("--- naive ---"); System.out.println(NaiveReport.build());
System.out.println("--- lazy ---");
var lazy=Lazy.of(() -> { System.out.println("BUILD"); return "report"; });
System.out.println("created"); System.out.println(lazy.get()); System.out.println(lazy.get());
System.out.println("--- io ---");
System.out.println(IO.delay(() -> "edge").map(s -> s+"!").unsafeRun());
  }
}
