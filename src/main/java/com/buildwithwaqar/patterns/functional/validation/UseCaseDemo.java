package com.buildwithwaqar.patterns.functional.validation;

import com.buildwithwaqar.patterns.functional.validation.SignupValidation;
/** Problem: signup form must show EVERY field error at once. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Validation problem: signup accumulates errors ===");
    System.out.println(SignupValidation.message(SignupValidation.signup("a@b.c", 30)));
    System.out.println(SignupValidation.message(SignupValidation.signup("bad", 10)));
  }
}
