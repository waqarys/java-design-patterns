package com.buildwithwaqar.patterns.concurrency.virtualthreads.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Virtual Threads: fan-out HTTP checks ===");
    System.out.println("--- naive ---"); System.out.println(NaiveChecks.run(200));
System.out.println("--- virtual threads ---");
System.out.println("checks="+new UrlChecker().checkAll(200));
  }
}
