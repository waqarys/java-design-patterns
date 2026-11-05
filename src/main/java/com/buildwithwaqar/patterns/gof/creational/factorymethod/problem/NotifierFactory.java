package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public abstract class NotifierFactory {
  public abstract Notifier create();
  public String notifyUser(String to, String body) { return create().send(to, body); }
}
