package com.buildwithwaqar.patterns.enterprise.di;

import com.buildwithwaqar.patterns.enterprise.di.*;
/** Problem: wire Greeter without hard-coding FriendlyGreeter in controllers. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== DI problem: wire greeter ===");
    var c = new DiContainer();
    c.singleton(Greeter.class, FriendlyGreeter::new);
    System.out.println(c.get(Greeter.class).greet("Waqar"));
    System.out.println("same=" + (c.get(Greeter.class) == c.get(Greeter.class)));
  }
}
