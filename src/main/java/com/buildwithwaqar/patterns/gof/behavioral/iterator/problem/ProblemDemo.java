package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Iterator: round-robin workers ===");
    System.out.println("--- naive ---");
var n=new NaiveAssign(List.of("w1","w2")); System.out.println(n.next()+" "+n.next()+" "+n.next());
System.out.println("--- iterator ---");
var it=new RoundRobin(List.of("w1","w2")).iterator();
System.out.println(it.next()+" "+it.next()+" "+it.next());
  }
}
