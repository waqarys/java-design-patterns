package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Producer-Consumer: encoder→uploader queue ===");
    System.out.println("--- naive ---"); NaivePipe.run(List.of("a","b"));
System.out.println("--- producer-consumer ---");
var q=new BoundedQueue<String>(2);
var done=new CountDownLatch(1); List<String> uploaded=new ArrayList<>();
Thread consumer=Thread.ofVirtual().start(() -> { try {
  for(int i=0;i<3;i++) uploaded.add(q.take());
  done.countDown();
} catch(InterruptedException e){ Thread.currentThread().interrupt(); }});
q.put("a"); q.put("b"); q.put("c"); done.await(2, TimeUnit.SECONDS);
System.out.println("uploaded="+uploaded);
  }
}
