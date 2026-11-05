package com.buildwithwaqar.patterns.functional.validation;

import com.buildwithwaqar.patterns.functional.validation.SignupValidation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void accumulates() {
    var m = SignupValidation.message(SignupValidation.signup("x", 1));
    assertTrue(m.contains("email")); assertTrue(m.contains("age"));
  }
}
