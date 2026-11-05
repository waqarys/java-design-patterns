package com.buildwithwaqar.patterns.functional.reader;

import java.util.function.Function;

/** Reader monad — computation that depends on an environment R. */
public record Reader<R, A>(Function<R, A> run) {
    public A run(R env) { return run.apply(env); }

    public <B> Reader<R, B> map(Function<? super A, ? extends B> f) {
        return new Reader<>(env -> f.apply(run(env)));
    }

    public <B> Reader<R, B> flatMap(Function<? super A, Reader<R, B>> f) {
        return new Reader<>(env -> f.apply(run(env)).run(env));
    }

    public static <R, A> Reader<R, A> pure(A a) { return new Reader<>(env -> a); }
    public static <R> Reader<R, R> ask() { return new Reader<>(Function.identity()); }
}

record Config(String greeting, String name) {}
