package com.buildwithwaqar.patterns.gof.behavioral.observer;

import com.buildwithwaqar.patterns.gof.behavioral.observer.EventBus;
import java.util.ArrayList;
/** Problem: fan out domain events to audit and UI without hardwiring. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Observer problem: event fan-out ===");
    var bus = new EventBus<String>();
    var audit = new ArrayList<String>();
    try (var s = bus.subscribe(e -> audit.add(e))) {
      bus.publish("video.published");
    }
    System.out.println("audit=" + audit);
  }
}
