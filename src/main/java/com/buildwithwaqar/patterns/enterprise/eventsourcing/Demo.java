package com.buildwithwaqar.patterns.enterprise.eventsourcing;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Event Sourcing / CQRS walkthrough ===");
    var account = new Account();
    account.deposit(100);
    account.withdraw(40);
    System.out.println("writeModelBalance=" + account.balance());
    System.out.println("events=" + account.events());
    var view = new BalanceView();
    account.events().forEach(view::project);
    System.out.println("readModelBalance=" + view.balance());
  }
}
