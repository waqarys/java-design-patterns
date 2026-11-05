package com.buildwithwaqar.patterns.concurrency.futurepromise;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Future/Promise walkthrough ===");
    System.out.println("async=" + Promise.computeAsync(21).toCompletableFuture().get());
    System.out.println("future=" + Promise.asFuture(3).get());
  }
}
