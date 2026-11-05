package com.buildwithwaqar.patterns.functional.try_;

import dev.zazr.control.Try;

public final class ZazrTryDemo {
    private ZazrTryDemo() {}

    public static Try<Integer> parse(String raw) {
        return Try.of(() -> Integer.parseInt(raw));
    }

    public static int portOrDefault(String raw, int fallback) {
        return parse(raw).catchAll(err -> fallback).get();
    }
}
