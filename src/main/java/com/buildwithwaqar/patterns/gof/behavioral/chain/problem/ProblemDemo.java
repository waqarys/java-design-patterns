package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Chain: support ticket routing ===");
    System.out.println("--- naive ---");
System.out.println(NaiveRouter.route("billing"));
System.out.println("--- chain ---");
var h=new BillingHandler(); h.link(new TechHandler()).link(new GeneralHandler());
System.out.println(h.handle("billing"));
System.out.println(h.handle("tech"));
System.out.println(h.handle("other"));
  }
}
