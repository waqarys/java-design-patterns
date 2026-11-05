package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Read-Write Lock: manifest cache ===");
    System.out.println("--- naive ---"); var n=new NaiveCache(); System.out.println(n.read()); n.write("v2"); System.out.println(n.read());
System.out.println("--- rw lock ---");
var c=new CachedManifest();
ExecutorService ex=Executors.newFixedThreadPool(4);
List<Future<String>> reads=new ArrayList<>();
for(int i=0;i<10;i++) reads.add(ex.submit(c::read));
c.write("v2");
System.out.println("final="+c.read());
ex.shutdown();
  }
}
