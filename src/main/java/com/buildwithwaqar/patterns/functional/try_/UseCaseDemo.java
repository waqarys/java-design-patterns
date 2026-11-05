package com.buildwithwaqar.patterns.functional.try_;

import com.buildwithwaqar.patterns.functional.try_.ZazrTryDemo;
/** Problem: parse PORT with a safe fallback when the env var is garbage. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Try problem: config int with recovery ===");
    System.out.println("good=" + ZazrTryDemo.portOrDefault("9090", 8080));
    System.out.println("bad=" + ZazrTryDemo.portOrDefault("abc", 8080));
  }
}
