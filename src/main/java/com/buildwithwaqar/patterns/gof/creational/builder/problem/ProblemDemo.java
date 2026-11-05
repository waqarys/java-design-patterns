package com.buildwithwaqar.patterns.gof.creational.builder.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Builder: immutable HTTP request ===");
    System.out.println("--- naive ---");
System.out.println(NaiveHttp.build("/x","POST","{}",true));
System.out.println("--- builder ---");
System.out.println(new HttpRequest.Builder().url("/v1/orders").method("POST").body("{}").auth(true).build().summarize());
try { new HttpRequest.Builder().method("GET").build(); } catch (IllegalArgumentException e) { System.out.println("caught="+e.getMessage()); }
  }
}
