package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;

/** Naive: client switches on channel strings and news concrete types. */
public final class NaiveNotify {
  public static String send(String channel, String to, String body) {
    if ("email".equals(channel)) return "email:" + to + ":" + body;
    if ("sms".equals(channel)) return "sms:" + to + ":" + body;
    if ("push".equals(channel)) return "push:" + to + ":" + body;
    throw new IllegalArgumentException("unknown channel " + channel);
  }
}
