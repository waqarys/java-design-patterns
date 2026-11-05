package com.buildwithwaqar.patterns.functional.adt;

import dev.zazr.control.Either;

/** Smart constructor — prefer {@link #parse}; canonical ctor still validates. */
public record Email(String value) {
    public Email {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("invalid email");
        }
    }

    public static Either<String, Email> parse(String raw) {
        if (raw == null || !raw.contains("@")) return Either.left("invalid email");
        return Either.right(new Email(raw));
    }
}
