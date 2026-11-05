package com.buildwithwaqar.patterns.functional.validation;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Validation walkthrough ===");
    System.out.println(SignupValidation.message(SignupValidation.signup("a@b.c", 21)));
    System.out.println(SignupValidation.message(SignupValidation.signup("jules", -1)));
  }
}
