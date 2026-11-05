package com.buildwithwaqar.patterns.functional.lens;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Lens walkthrough ===");
    var p = new Person("Ada", new Address("London", "E1"));
    var updated = PersonLenses.personCity.modify(p, c -> "Paris");
    System.out.println("before=" + p);
    System.out.println("after=" + updated);
  }
}
