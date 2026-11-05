package com.buildwithwaqar.patterns.enterprise.eventsourcing.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Event Sourcing: bank ledger + balance ===");
    System.out.println("--- naive ---"); var n=new NaiveAccount(); n.deposit(100); n.withdraw(40); System.out.println(n.bal());
System.out.println("--- event sourcing ---");
var a=new Account(); a.deposit(100); a.withdraw(40); System.out.println("bal="+a.balance()); System.out.println("events="+a.history());
try { a.withdraw(1000); } catch(IllegalStateException e){ System.out.println("caught="+e.getMessage()); }
  }
}
