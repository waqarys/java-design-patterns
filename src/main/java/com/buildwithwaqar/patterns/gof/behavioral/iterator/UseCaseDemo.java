package com.buildwithwaqar.patterns.gof.behavioral.iterator;

import com.buildwithwaqar.patterns.gof.behavioral.iterator.RoundRobin;
import java.util.List;
/** Problem: distribute jobs fairly across workers forever. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Iterator problem: round-robin workers ===");
    var it = new RoundRobin<>(List.of("w1","w2","w3")).iterator();
    for (int i=0;i<5;i++) System.out.println("job"+i+"->" + it.next());
  }
}
