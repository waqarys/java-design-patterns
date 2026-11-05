package com.buildwithwaqar.patterns.functional.monoid;

import java.util.List;

interface Semigroup<A> {
    A combine(A a, A b);
}

public interface Monoid<A> extends Semigroup<A> {
    A empty();

    default A fold(List<A> xs) {
        A acc = empty();
        for (A x : xs) acc = combine(acc, x);
        return acc;
    }
}
