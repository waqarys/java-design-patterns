package com.buildwithwaqar.patterns.enterprise.saga;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Saga walkthrough ===");
    var ok = new OrderSaga(); ok.run(() -> true);
    System.out.println("success=" + ok.log());
    var bad = new OrderSaga(); bad.run(() -> false);
    System.out.println("compensated=" + bad.log());
  }
}
