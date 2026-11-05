package com.buildwithwaqar.patterns.functional.typeclass;

import dev.zazr.control.Option;

import java.util.function.Function;

/** Witness that Zazr Option forms a Monad. */
public final class OptionKind {
    private OptionKind() {}

    public enum Witness {}

    public record Repr<A>(Option<A> value) implements Kind<Witness, A> {}

    public static <A> Repr<A> lift(Option<A> o) { return new Repr<>(o); }
    public static <A> Option<A> lower(Kind<Witness, A> k) { return ((Repr<A>) k).value(); }

    public static final Monad<Witness> MONAD = new Monad<>() {
        public <A> Kind<Witness, A> pure(A a) { return lift(Option.some(a)); }

        public <A, B> Kind<Witness, B> flatMap(Kind<Witness, A> fa, Function<? super A, ? extends Kind<Witness, B>> f) {
            return lift(lower(fa).flatMap(a -> lower(f.apply(a))));
        }
    };
}
