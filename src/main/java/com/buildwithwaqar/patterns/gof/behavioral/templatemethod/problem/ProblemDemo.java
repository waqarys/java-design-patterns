package com.buildwithwaqar.patterns.gof.behavioral.templatemethod.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Template Method: CSV/JSON mine ===");
    System.out.println("--- naive ---"); System.out.println(NaiveMine.csv("a\nb"));
System.out.println("--- template ---");
System.out.println(new CsvMiner().mine("a\nb\n"));
System.out.println(new JsonMiner().mine("{\n x:1\n}\n"));
  }
}
