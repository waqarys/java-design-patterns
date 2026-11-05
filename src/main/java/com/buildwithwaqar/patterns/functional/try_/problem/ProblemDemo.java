package com.buildwithwaqar.patterns.functional.try_.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Try: parse config int with recovery ===");
    System.out.println("--- naive ---");
try { System.out.println(NaiveCfg.port("x")); } catch(Exception e){ System.out.println("caught"); }
System.out.println("--- try ---");
System.out.println(Tries.of(() -> Integer.parseInt("8080")).getOrElse(-1));
System.out.println(Tries.of(() -> Integer.parseInt("x")).getOrElse(8080));
  }
}
