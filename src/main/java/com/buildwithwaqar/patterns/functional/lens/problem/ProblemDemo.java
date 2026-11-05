package com.buildwithwaqar.patterns.functional.lens.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Lenses: nested city update ===");
    System.out.println("--- naive ---"); System.out.println(NaivePerson.move("{city:Old}", "NYC"));
System.out.println("--- lenses ---");
var p=new Person("Ada", new Address("Old", "10001"));
var moved=PersonLenses.personCity.setA(p, "NYC");
System.out.println(p); System.out.println(moved);
System.out.println("city="+PersonLenses.personCity.getA(moved));
  }
}
