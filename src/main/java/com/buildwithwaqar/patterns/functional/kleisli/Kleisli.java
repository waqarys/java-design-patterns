package com.buildwithwaqar.patterns.functional.kleisli;

import com.buildwithwaqar.patterns.functional.typeclass.Kind;
import com.buildwithwaqar.patterns.functional.typeclass.Monad;

import java.util.function.Function;

/** Kleisli arrow A =&gt; F&lt;B&gt; — composable effectful functions. */
public record Kleisli<F, A, B>(Function<A, Kind<F, B>> run) {
    public Kind<F, B> apply(A a) { return run.apply(a); }

    public <C> Kleisli<F, A, C> andThen(Monad<F> M, Kleisli<F, B, C> next) {
        return new Kleisli<>(a -> M.flatMap(run.apply(a), next.run));
    }

    public static <F, A, B> Kleisli<F, A, B> of(Function<A, Kind<F, B>> f) {
        return new Kleisli<>(f);
    }
}
