package com.buildwithwaqar.patterns.enterprise.di;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== DI walkthrough ===");
    var c = new DiContainer();
    c.singleton(Greeter.class, FriendlyGreeter::new);
    var g1 = c.get(Greeter.class);
    var g2 = c.get(Greeter.class);
    System.out.println("same=" + (g1 == g2));
    System.out.println(g1.greet("Waqar"));
  }
}
