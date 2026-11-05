package com.buildwithwaqar.patterns.enterprise.nullobject;

import com.buildwithwaqar.patterns.enterprise.nullobject.*;
/** Problem: disable logging without null checks at every call site. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Null Object problem: optional logger ===");
    System.out.println(new OrderService(null).place("o1"));
    var sink = new StringBuilder();
    System.out.println(new OrderService(new ConsoleLogger(sink)).place("o2"));
    System.out.println("log=" + sink.toString().trim());
  }
}
