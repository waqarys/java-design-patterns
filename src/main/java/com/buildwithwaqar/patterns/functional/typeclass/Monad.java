package com.buildwithwaqar.patterns.functional.typeclass;

import java.util.function.Function;

public interface Monad<F> extends Applicative<F> {
    <A, B> Kind<F, B> flatMap(Kind<F, A> fa, Function<? super A, ? extends Kind<F, B>> f);

    @Override
    default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<? super A, ? extends B> f) {
        return flatMap(fa, a -> pure(f.apply(a)));
    }

    @Override
    default <A, B> Kind<F, B> ap(Kind<F, Function<A, B>> ff, Kind<F, A> fa) {
        return flatMap(ff, f -> map(fa, f));
    }
}
