package com.buildwithwaqar.patterns.functional.validation;

import dev.zazr.control.Validation;

record Signup(String email, int age) {}

public final class SignupValidation {
    private SignupValidation() {}

    public static Validation<String, String> email(String raw) {
        return Validation.fromPredicate(raw, e -> e != null && e.contains("@"), e -> "email has no @");
    }

    public static Validation<String, Integer> age(int raw) {
        return Validation.fromPredicate(raw, a -> a >= 18, a -> "age under 18");
    }

    public static Validation<String, Signup> signup(String email, int age) {
        return Validation.zipWith(email(email), age(age), Signup::new);
    }

    public static String message(Validation<String, Signup> v) {
        return switch (v) {
            case Validation.Valid(var s) -> "welcome " + s.email();
            case Validation.Invalid(var errors) -> errors.mkString(", ");
        };
    }
}
