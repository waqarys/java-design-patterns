package com.buildwithwaqar.patterns.functional.cake;

import com.buildwithwaqar.patterns.functional.cake.CakePattern;
/** Problem: assemble UserRepo + EmailService into UserService without a DI framework. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Cake problem: wire user welcome flow ===");
    System.out.println(new CakePattern.App().userService().welcome("42"));
  }
}
