package com.buildwithwaqar.patterns.functional.either;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Either / Railway walkthrough ===");
    System.out.println(Railway.explain(Railway.label("7")));
    System.out.println(Railway.explain(Railway.label("x")));
    System.out.println(Railway.explain(Railway.label("-3")));
  }
}
