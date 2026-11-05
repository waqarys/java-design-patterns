package com.buildwithwaqar.patterns.functional.state;

import java.util.function.Function;

public record State<S, A>(Function<S, Result<S, A>> run) {
    public record Result<S, A>(S state, A value) {}

    public Result<S, A> run(S s) { return run.apply(s); }

    public <B> State<S, B> map(Function<? super A, ? extends B> f) {
        return new State<>(s -> {
            var r = run(s);
            return new Result<>(r.state(), f.apply(r.value()));
        });
    }

    public <B> State<S, B> flatMap(Function<? super A, State<S, B>> f) {
        return new State<>(s -> {
            var r = run(s);
            return f.apply(r.value()).run(r.state());
        });
    }

    public static <S, A> State<S, A> pure(A a) { return new State<>(s -> new Result<>(s, a)); }
    public static <S> State<S, S> get() { return new State<>(s -> new Result<>(s, s)); }
    public static <S> State<S, Void> set(S s) { return new State<>(ignored -> new Result<>(s, null)); }
    public static <S> State<S, Void> modify(Function<S, S> f) {
        return new State<>(s -> new Result<>(f.apply(s), null));
    }
}
