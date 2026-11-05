package com.buildwithwaqar.patterns.enterprise.circuitbreaker.problem;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Circuit Breaker: flaky rates API ===");
    System.out.println("--- naive ---");
    var n=new NaiveRates();
    for(int i=0;i<4;i++){ try{ System.out.println(n.rate()); } catch(Exception e){ System.out.println("fail"); } }
    System.out.println("--- circuit breaker ---");
    AtomicInteger blows=new AtomicInteger();
    var cb=new CircuitBreaker(2, 10);
    Supplier<String> flaky=() -> { if(blows.getAndIncrement()<2) throw new RuntimeException("down"); return "rate=83.2"; };
    for (int i=0;i<2;i++){ try{ System.out.println(cb.call(flaky)); } catch(Exception e){ System.out.println("fail state="+cb.state()); } }
    try { cb.call(flaky); } catch(IllegalStateException e){ System.out.println("shortCircuit="+e.getMessage()+" state="+cb.state()); }
    Thread.sleep(15); blows.set(100);
    System.out.println("recovered="+cb.call(() -> "rate=83.2")+" state="+cb.state());
  }
}
