package com.buildwithwaqar.patterns.gof.structural.facade;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Facade walkthrough ===");
    var facade = new OrderFacade();
    System.out.println(facade.placeOrder("sku-42", 1, "4111", 1999));
    System.out.println(facade.placeOrder("", 1, "4111", 1999));
    System.out.println(facade.placeOrder("sku-42", 1, "4111", 0));
  }
}
