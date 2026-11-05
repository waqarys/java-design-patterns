package com.buildwithwaqar.patterns.functional.typeclass;

import java.util.function.Function;

public interface Functor<F> {
    <A, B> Kind<F, B> map(Kind<F, A> fa, Function<? super A, ? extends B> f);
}
