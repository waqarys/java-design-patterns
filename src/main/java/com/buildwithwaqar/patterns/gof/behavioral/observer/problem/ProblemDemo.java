package com.buildwithwaqar.patterns.gof.behavioral.observer.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Observer: video.published fan-out ===");
    System.out.println("--- naive ---"); NaivePublish.publish("v1");
System.out.println("--- observer ---");
var bus=new EventBus(); List<String> log=new ArrayList<>();
bus.on("video.published", (e,id)->log.add("audit:"+id));
bus.on("video.published", (e,id)->log.add("ui:"+id));
bus.publish("video.published","v1");
bus.publish("other","x");
System.out.println(log);
  }
}
