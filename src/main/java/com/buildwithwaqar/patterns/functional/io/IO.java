package com.buildwithwaqar.patterns.functional.io;

import java.util.function.Function;
import java.util.function.Supplier;

/** Tiny IO effect — suspend side effects, interpret at the edge. */
public final class IO<A> {
    private final Supplier<A> thunk;

    private IO(Supplier<A> thunk) { this.thunk = thunk; }

    public static <A> IO<A> delay(Supplier<A> thunk) { return new IO<>(thunk); }
    public static <A> IO<A> pure(A a) { return new IO<>(() -> a); }

    public <B> IO<B> map(Function<? super A, ? extends B> f) {
        return new IO<>(() -> f.apply(thunk.get()));
    }

    public <B> IO<B> flatMap(Function<? super A, IO<B>> f) {
        return new IO<>(() -> f.apply(thunk.get()).unsafeRun());
    }

    /** Unsafe: runs effects. Call only at the program edge. */
    public A unsafeRun() { return thunk.get(); }
}
