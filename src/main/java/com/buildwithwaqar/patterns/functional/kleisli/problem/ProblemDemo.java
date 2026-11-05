package com.buildwithwaqar.patterns.functional.kleisli.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Kleisli: parse → enrich → authorize ===");
    System.out.println("--- naive ---"); System.out.println(NaiveAuth.auth("7"));
System.out.println("--- kleisli ---");
System.out.println(AuthPipe.pipeline.apply("7"));
System.out.println(AuthPipe.pipeline.apply("x"));
System.out.println(AuthPipe.pipeline.apply("-1"));
  }
}
