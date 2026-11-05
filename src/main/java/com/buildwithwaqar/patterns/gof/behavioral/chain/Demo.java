package com.buildwithwaqar.patterns.gof.behavioral.chain;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Chain walkthrough ===");
    var root = new BillingHandler();
    root.linkWith(new TechHandler()).linkWith(new GeneralHandler());
    for (String r : new String[]{"bill:42", "tech:wifi", "hello"}) {
      System.out.println(r + " -> " + root.handle(r));
    }
  }
}
