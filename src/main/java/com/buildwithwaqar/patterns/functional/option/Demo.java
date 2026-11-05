package com.buildwithwaqar.patterns.functional.option;
import dev.zazr.control.Option;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Option walkthrough ===");
    System.out.println(ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("3")));
    System.out.println(ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("-1")));
    System.out.println("sum=" + ZazrOptionDemo.sum3(Option.some(1), Option.some(2), Option.some(3)));
    System.out.println("sumNone=" + ZazrOptionDemo.sum3(Option.some(1), Option.none(), Option.some(3)));
  }
}
