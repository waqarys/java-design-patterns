package com.buildwithwaqar.patterns.gof.structural.facade;
import com.buildwithwaqar.patterns.gof.structural.facade.OrderFacade;
/** Problem: place an order without teaching every caller inventory+payment+shipping APIs. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Facade problem: one-call checkout ===");
    var f = new OrderFacade();
    System.out.println(f.placeOrder("sku-1", 1, "4111", 1500));
    System.out.println(f.placeOrder("", 1, "4111", 1500));
  }
}
