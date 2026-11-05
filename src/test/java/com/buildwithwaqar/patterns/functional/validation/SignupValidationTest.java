package com.buildwithwaqar.patterns.functional.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignupValidationTest {
    @Test void collectsAllErrors() {
        var msg = SignupValidation.message(SignupValidation.signup("jules", -1));
        assertTrue(msg.contains("email has no @"));
        assertTrue(msg.contains("age under 18"));
    }

    @Test void valid() {
        assertEquals("welcome a@b.c", SignupValidation.message(SignupValidation.signup("a@b.c", 21)));
    }
}
