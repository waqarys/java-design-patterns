package com.buildwithwaqar.patterns.functional.cake;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Cake Pattern walkthrough ===");
    var app = new CakePattern.App();
    System.out.println(app.userService().welcome("42"));
  }
}
