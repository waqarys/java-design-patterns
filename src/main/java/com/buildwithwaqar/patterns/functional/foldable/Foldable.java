package com.buildwithwaqar.patterns.functional.foldable;

import com.buildwithwaqar.patterns.functional.monoid.Monoid;
import com.buildwithwaqar.patterns.functional.typeclass.Applicative;
import com.buildwithwaqar.patterns.functional.typeclass.Kind;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/** Foldable + Traverse for List — educational encodings. */
public final class Foldable {
    private Foldable() {}

    public static <A, B> B foldLeft(List<A> xs, B z, BiFunction<B, A, B> f) {
        B acc = z;
        for (A x : xs) acc = f.apply(acc, x);
        return acc;
    }

    public static <A> A fold(List<A> xs, Monoid<A> m) {
        return foldLeft(xs, m.empty(), m::combine);
    }

    public static <F, A, B> Kind<F, List<B>> traverse(Applicative<F> A, List<A> xs, Function<A, Kind<F, B>> f) {
        Kind<F, List<B>> acc = A.pure(new ArrayList<>());
        for (A x : xs) {
            Kind<F, B> fb = f.apply(x);
            acc = A.ap(A.map(acc, list -> (b -> { list.add(b); return list; })), fb);
        }
        return acc;
    }
}
