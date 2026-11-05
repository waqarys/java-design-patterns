package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Factory Method: Email/SMS/Push notifiers ===");
    System.out.println("--- naive ---");
    System.out.println(NaiveNotify.send("email", "a@b.c", "hi"));
    try { NaiveNotify.send("fax", "a", "x"); } catch (IllegalArgumentException e) { System.out.println("caught=" + e.getMessage()); }
    System.out.println("--- factory method ---");
    NotifierFactory email = new EmailFactory();
    NotifierFactory sms = new SmsFactory();
    System.out.println(email.notifyUser("a@b.c", "hi"));
    System.out.println(sms.notifyUser("+1", "hi"));
    System.out.println(new PushFactory().notifyUser("device", "hi"));
  }
}
