package com.buildwithwaqar.patterns.enterprise.eventsourcing;

import com.buildwithwaqar.patterns.enterprise.eventsourcing.*;
/** Problem: reconstruct account balance from an append-only ledger. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Event Sourcing problem: bank ledger ===");
    var acct = new Account();
    acct.deposit(100); acct.withdraw(30);
    System.out.println("balance=" + acct.balance());
    System.out.println("events=" + acct.events());
    var view = new BalanceView();
    acct.events().forEach(view::project);
    System.out.println("readModel=" + view.balance());
  }
}
