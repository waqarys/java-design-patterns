package com.buildwithwaqar.patterns.functional.either;

import com.buildwithwaqar.patterns.functional.either.Railway;
/** Problem: validate an ID through parse→positive without try/catch ladders. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Either problem: railway ID validation ===");
    System.out.println(Railway.explain(Railway.label("42")));
    System.out.println(Railway.explain(Railway.label("nope")));
    System.out.println(Railway.explain(Railway.label("-1")));
  }
}
