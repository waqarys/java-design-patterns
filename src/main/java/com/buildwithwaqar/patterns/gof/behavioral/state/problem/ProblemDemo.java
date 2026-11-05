package com.buildwithwaqar.patterns.gof.behavioral.state.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== State: order lifecycle ===");
    System.out.println("--- naive (illegal ship) ---");
var n=new NaiveOrder(); n.ship(); System.out.println(n.state);
System.out.println("--- state ---");
var o=new Order(); o.pay(); o.ship(); System.out.println(o.phase());
try { new Order().ship(); } catch (IllegalStateException e){ System.out.println("caught="+e.getMessage()); }
  }
}
