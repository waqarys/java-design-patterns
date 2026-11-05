package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public record EmailNotifier() implements Notifier {
  public String send(String to, String body) { return "email:" + to + ":" + body; }
}
