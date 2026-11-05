package com.buildwithwaqar.patterns.enterprise.di.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== DI: wire Greeter without hard new ===");
    System.out.println("--- naive ---"); System.out.println(new NaiveController().hello("Ada"));
System.out.println("--- DI ---");
var c=new DiContainer(); c.register(Greeter.class, new FormalGreeter());
var ctl=new HelloController(c.get(Greeter.class));
System.out.println(ctl.hello("Ada"));
try { new DiContainer().get(Greeter.class); } catch (IllegalStateException e){ System.out.println("caught="+e.getMessage()); }
  }
}
