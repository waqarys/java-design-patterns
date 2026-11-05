package com.buildwithwaqar.patterns.gof.behavioral.state;

import com.buildwithwaqar.patterns.gof.behavioral.state.OrderStateMachine;
/** Problem: enforce order lifecycle transitions and reject illegal ones. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== State problem: order lifecycle ===");
    var o = new OrderStateMachine();
    System.out.println(o.status());
    o.pay(); System.out.println("paid=" + o.status());
    o.ship(); System.out.println("shipped=" + o.status());
    try { o.cancel(); } catch (IllegalStateException e) { System.out.println("illegalCancel=caught"); }
  }
}
