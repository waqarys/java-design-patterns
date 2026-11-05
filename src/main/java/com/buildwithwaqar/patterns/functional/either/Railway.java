package com.buildwithwaqar.patterns.functional.either;

import dev.zazr.control.Either;

/** Railway-oriented programming with Zazr Either. */
public final class Railway {
    private Railway() {}

    public static Either<String, Integer> parseInt(String raw) {
        try {
            return Either.right(Integer.parseInt(raw));
        } catch (NumberFormatException e) {
            return Either.left("not an int: " + raw);
        }
    }

    public static Either<String, Integer> requirePositive(int n) {
        return n > 0 ? Either.right(n) : Either.left("not positive: " + n);
    }

    public static Either<String, String> label(String raw) {
        return parseInt(raw)
                .flatMap(Railway::requirePositive)
                .map(n -> "id-" + n);
    }

    public static String explain(Either<String, String> result) {
        return switch (result) {
            case Either.Right(var v) -> "ok:" + v;
            case Either.Left(var err) -> "err:" + err;
        };
    }
}
