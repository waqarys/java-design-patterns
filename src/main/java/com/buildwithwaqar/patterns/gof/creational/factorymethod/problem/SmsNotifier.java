package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public record SmsNotifier() implements Notifier {
  public String send(String to, String body) { return "sms:" + to + ":" + body; }
}
