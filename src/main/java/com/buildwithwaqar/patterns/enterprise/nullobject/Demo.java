package com.buildwithwaqar.patterns.enterprise.nullobject;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Null Object walkthrough ===");
    System.out.println(new OrderService(null).place("ord-1"));
    var sink = new StringBuilder();
    System.out.println(new OrderService(new ConsoleLogger(sink)).place("ord-2"));
    System.out.println("log=" + sink.toString().trim());
  }
}
