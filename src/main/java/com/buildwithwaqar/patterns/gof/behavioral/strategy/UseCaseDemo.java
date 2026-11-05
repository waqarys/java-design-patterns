package com.buildwithwaqar.patterns.gof.behavioral.strategy;

import com.buildwithwaqar.patterns.gof.behavioral.strategy.*;
/** Problem: swap cart discount algorithms at checkout. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Strategy problem: cart pricing ===");
    System.out.println("10off=" + new Cart(PricingStrategy.percentageOff(10)).checkout(1000));
    System.out.println("flat=" + new Cart(PricingStrategy.flatOff(150)).checkout(1000));
  }
}
