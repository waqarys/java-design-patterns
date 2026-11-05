package com.buildwithwaqar.patterns.concurrency.virtualthreads;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Virtual Threads walkthrough ===");
    System.out.println("fanOut=" + VirtualThreadDemo.fanOut(50));
  }
}
