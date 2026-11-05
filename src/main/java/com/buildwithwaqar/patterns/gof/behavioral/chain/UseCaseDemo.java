package com.buildwithwaqar.patterns.gof.behavioral.chain;

import com.buildwithwaqar.patterns.gof.behavioral.chain.*;
/** Problem: route support tickets without a giant if/else in the controller. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Chain problem: support ticket routing ===");
    var root = new BillingHandler();
    root.linkWith(new TechHandler()).linkWith(new GeneralHandler());
    for (String t : new String[]{"bill:invoice", "tech:5xx", "hi"}) System.out.println(t + "->" + root.handle(t));
  }
}
