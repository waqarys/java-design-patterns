package com.buildwithwaqar.patterns.functional.writer;

import com.buildwithwaqar.patterns.functional.monoid.Monoid;

import java.util.function.Function;

public record Writer<W, A>(W log, A value) {
    public <B> Writer<W, B> map(Function<? super A, ? extends B> f) {
        return new Writer<>(log, f.apply(value));
    }

    public <B> Writer<W, B> flatMap(Monoid<W> monoid, Function<? super A, Writer<W, B>> f) {
        var next = f.apply(value);
        return new Writer<>(monoid.combine(log, next.log), next.value);
    }

    public static <W, A> Writer<W, A> pure(Monoid<W> monoid, A a) {
        return new Writer<>(monoid.empty(), a);
    }

    public static <W> Writer<W, Void> tell(W w) { return new Writer<>(w, null); }
}
