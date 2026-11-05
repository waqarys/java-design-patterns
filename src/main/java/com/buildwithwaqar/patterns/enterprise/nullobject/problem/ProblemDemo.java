package com.buildwithwaqar.patterns.enterprise.nullobject.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Null Object: safe logger ===");
    System.out.println("--- naive null check ---");
new NaiveOrderService(null).place("o1");
System.out.println("--- null object ---");
var console=new ConsoleLogger();
new OrderService(console).place("o1");
new OrderService(new NullLogger()).place("o2");
System.out.println(console.lines());
  }
}
