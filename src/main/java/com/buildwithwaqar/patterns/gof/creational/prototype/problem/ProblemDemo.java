package com.buildwithwaqar.patterns.gof.creational.prototype.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Prototype: clone meeting templates ===");
    System.out.println("--- naive ---");
System.out.println(NaiveTemplate.clone("Weekly"));
System.out.println("--- prototype ---");
var proto = new MeetingTemplate("Weekly Sync", List.of("Ada","Bob"));
var teamA = proto.copy().withTitle("Team A Sync");
System.out.println(proto.describe());
System.out.println(teamA.describe());
System.out.println("independent=" + (proto.attendees() != teamA.attendees()));
  }
}
