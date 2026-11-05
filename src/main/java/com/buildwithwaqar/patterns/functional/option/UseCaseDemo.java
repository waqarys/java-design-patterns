package com.buildwithwaqar.patterns.functional.option;

import com.buildwithwaqar.patterns.functional.option.ZazrOptionDemo;
/** Problem: parse a positive port from config without null or exceptions at call sites. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Option problem: positive port ===");
    System.out.println(ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("8080")));
    System.out.println(ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("-1")));
  }
}
