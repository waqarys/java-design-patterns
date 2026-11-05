package com.buildwithwaqar.patterns.gof.behavioral.strategy;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Strategy walkthrough ===");
    System.out.println("pct10=" + new Cart(PricingStrategy.percentageOff(10)).checkout(1000));
    System.out.println("flat200=" + new Cart(PricingStrategy.flatOff(200)).checkout(1000));
    PricingStrategy half = base -> base / 2;
    System.out.println("half=" + new Cart(half).checkout(1000));
  }
}
