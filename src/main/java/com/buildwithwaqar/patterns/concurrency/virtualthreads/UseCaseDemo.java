package com.buildwithwaqar.patterns.concurrency.virtualthreads;

import com.buildwithwaqar.patterns.concurrency.virtualthreads.VirtualThreadDemo;
/** Problem: fan-out thousands of blocking health checks without exhausting OS threads. */
public final class UseCaseDemo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Virtual Threads problem: massive fan-out ===");
    System.out.println("checks=" + VirtualThreadDemo.fanOut(200));
  }
}
