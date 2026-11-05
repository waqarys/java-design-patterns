package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public record PushNotifier() implements Notifier {
  public String send(String to, String body) { return "push:" + to + ":" + body; }
}
