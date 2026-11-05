package com.buildwithwaqar.patterns.concurrency.activeobject;

import com.buildwithwaqar.patterns.concurrency.activeobject.ActiveObject;
/** Problem: serialize ledger mutations without external locks in callers. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Active Object problem: serialized ledger ===");
    try (var ao = new ActiveObject()) {
      ao.add(10).get(); ao.add(-3).get();
      System.out.println("balance=" + ao.get().get());
    }
  }
}
