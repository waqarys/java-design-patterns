package com.buildwithwaqar.patterns.functional.either.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Either: user id railway ===");
    System.out.println("--- naive ---");
try { System.out.println(NaiveUser.load(null)); } catch(Exception e){ System.out.println("caught"); }
System.out.println("--- either ---");
System.out.println(UserPipeline.run("42"));
System.out.println(UserPipeline.run("x"));
System.out.println(UserPipeline.run("-1"));
  }
}
