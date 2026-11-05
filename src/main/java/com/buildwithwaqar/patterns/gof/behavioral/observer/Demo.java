package com.buildwithwaqar.patterns.gof.behavioral.observer;
import java.util.ArrayList;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Observer walkthrough ===");
    var bus = new EventBus<String>();
    var audit = new ArrayList<String>();
    var ui = new ArrayList<String>();
    try (var s1 = bus.subscribe(e -> audit.add("audit:" + e));
         var s2 = bus.subscribe(e -> ui.add("ui:" + e))) {
      bus.publish("video.published");
      bus.publish("video.transcoded");
    }
    bus.publish("ignored-after-close");
    System.out.println("audit=" + audit);
    System.out.println("ui=" + ui);
  }
}
