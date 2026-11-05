package com.buildwithwaqar.patterns.gof.behavioral.state;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== State walkthrough ===");
    var o = new OrderStateMachine();
    System.out.println("start=" + o.status());
    o.pay();
    System.out.println("afterPay=" + o.status());
    o.ship();
    System.out.println("afterShip=" + o.status());
    try { o.cancel(); } catch (IllegalStateException e) {
      System.out.println("cancelOnShipped=" + e.getClass().getSimpleName());
    }
  }
}
