package com.buildwithwaqar.patterns.functional.option;

import dev.zazr.control.Option;

import static dev.zazr.control.Option.none;
import static dev.zazr.control.Option.some;

/** Option / Maybe with Zazr — sealed Some/None, pattern-matchable. */
public final class ZazrOptionDemo {
    private ZazrOptionDemo() {}

    public static Option<Integer> parsePositive(String raw) {
        try {
            int n = Integer.parseInt(raw);
            return n > 0 ? some(n) : none();
        } catch (NumberFormatException e) {
            return none();
        }
    }

    public static String describe(Option<Integer> opt) {
        return switch (opt) {
            case Option.Some(var n) -> "got " + n;
            case Option.None() -> "missing";
        };
    }

    public static Option<Integer> sum3(Option<Integer> a, Option<Integer> b, Option<Integer> c) {
        return Option.zipWith(a, b, c, (x, y, z) -> x + y + z);
    }
}
