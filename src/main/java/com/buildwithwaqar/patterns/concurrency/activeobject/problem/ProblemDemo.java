package com.buildwithwaqar.patterns.concurrency.activeobject.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Active Object: serialize ledger ===");
    System.out.println("--- naive ---"); var n=new NaiveLedger(); n.deposit(5); System.out.println(n.bal());
System.out.println("--- active object ---");
try (var a=new ActiveLedger()) {
  a.deposit(10).join(); a.deposit(5).join();
  System.out.println("bal="+a.balance().join());
}
  }
}
