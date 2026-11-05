package com.buildwithwaqar.patterns.concurrency.activeobject;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Active Object walkthrough ===");
    try (var ao = new ActiveObject()) {
      System.out.println("add2=" + ao.add(2).get());
      System.out.println("add3=" + ao.add(3).get());
      System.out.println("get=" + ao.get().get());
    }
  }
}
