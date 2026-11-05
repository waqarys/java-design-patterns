package com.buildwithwaqar.patterns.functional.try_;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Try walkthrough ===");
    System.out.println("good=" + ZazrTryDemo.portOrDefault("80", 8080));
    System.out.println("bad=" + ZazrTryDemo.portOrDefault("nope", 8080));
  }
}
