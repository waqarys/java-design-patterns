package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Future/Promise: async thumbnail URL ===");
    System.out.println("--- naive ---"); System.out.println(NaiveThumb.url("a"));
System.out.println("--- future ---");
var s=new ThumbService();
System.out.println(s.render("vid").thenCompose(s::toUrl).join());
try { s.render("").join(); } catch(CompletionException e){ System.out.println("caught="+e.getCause().getMessage()); }
  }
}
