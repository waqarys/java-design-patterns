package com.buildwithwaqar.patterns.functional.lens;

import com.buildwithwaqar.patterns.functional.lens.*;
/** Problem: update nested city in immutable Person without manual copying. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Lens problem: nested immutable update ===");
    var p = new Person("Ada", new Address("London", "E1"));
    var q = PersonLenses.personCity.modify(p, c -> "Paris");
    System.out.println("before=" + p);
    System.out.println("after=" + q);
  }
}
