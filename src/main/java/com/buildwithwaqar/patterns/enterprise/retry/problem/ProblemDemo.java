package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.concurrent.atomic.AtomicInteger;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Retry: transient broker errors ===");
    System.out.println("--- naive ---");
    try { NaivePublish.once(() -> { throw new RuntimeException("broker"); }); } catch(Exception e){ System.out.println("caught="+e.getMessage()); }
    System.out.println("--- retry ---");
    AtomicInteger n=new AtomicInteger();
    System.out.println(Retry.call(3, () -> { if(n.incrementAndGet()<3) throw new RuntimeException("broker"); return "published"; }));
    try { Retry.call(2, () -> { throw new RuntimeException("still"); }); } catch(RuntimeException e){ System.out.println("caught="+e.getMessage()); }
  }
}
