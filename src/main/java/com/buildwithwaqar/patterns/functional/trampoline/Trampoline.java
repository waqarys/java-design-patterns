package com.buildwithwaqar.patterns.functional.trampoline;

import java.util.function.Supplier;

/** Stack-safe recursion via trampolining. */
public sealed interface Trampoline<A> permits Trampoline.Done, Trampoline.More {
    record Done<A>(A value) implements Trampoline<A> {}
    record More<A>(Supplier<Trampoline<A>> next) implements Trampoline<A> {}

    static <A> Trampoline<A> done(A a) { return new Done<>(a); }
    static <A> Trampoline<A> more(Supplier<Trampoline<A>> next) { return new More<>(next); }

    default A run() {
        Trampoline<A> cur = this;
        while (true) {
            switch (cur) {
                case Done(var v) -> { return v; }
                case More(var next) -> cur = next.get();
            }
        }
    }

    static Trampoline<Long> safeSum(long n, long acc) {
        if (n == 0) return done(acc);
        return more(() -> safeSum(n - 1, acc + n));
    }
}
