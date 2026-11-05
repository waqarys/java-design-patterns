package com.buildwithwaqar.patterns.concurrency.threadpool.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Thread Pool: cap thumbnail jobs ===");
    System.out.println("--- naive ---"); System.out.println(NaiveThumbs.run(5));
System.out.println("--- thread pool ---");
var pool=new SimpleThreadPool(2); List<Future<Integer>> fs=new ArrayList<>();
for(int i=0;i<5;i++){ int id=i; fs.add(pool.submit(() -> id*id)); }
System.out.println(fs.stream().map(f -> { try { return f.get(); } catch(Exception e){ throw new RuntimeException(e);} }).toList());
pool.shutdown();
  }
}
