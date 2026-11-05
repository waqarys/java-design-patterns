package com.buildwithwaqar.patterns.gof.behavioral.iterator;
import java.util.List;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Iterator walkthrough ===");
    var it = new RoundRobin<>(List.of("worker-a", "worker-b", "worker-c")).iterator();
    for (int i = 0; i < 5; i++) System.out.println("next=" + it.next());
  }
}
