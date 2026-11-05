package com.buildwithwaqar.patterns.functional.typeclass;

import java.util.function.Function;

public interface Applicative<F> extends Functor<F> {
    <A> Kind<F, A> pure(A a);
    <A, B> Kind<F, B> ap(Kind<F, Function<A, B>> ff, Kind<F, A> fa);
}
