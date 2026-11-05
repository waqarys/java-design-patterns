package com.buildwithwaqar.patterns.functional.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

/** Functional getter+setter pair for immutable updates. */
public record Lens<S, A>(Function<S, A> get, BiFunction<S, A, S> set) {
    public S modify(S s, Function<A, A> f) { return set.apply(s, f.apply(get.apply(s))); }

    public <B> Lens<S, B> andThen(Lens<A, B> other) {
        return new Lens<>(
                s -> other.get.apply(get.apply(s)),
                (s, b) -> set.apply(s, other.set.apply(get.apply(s), b))
        );
    }
}

record Address(String city, String zip) {}
record Person(String name, Address address) {}

final class PersonLenses {
    static final Lens<Person, String> name = new Lens<>(Person::name, (p, n) -> new Person(n, p.address()));
    static final Lens<Person, Address> address = new Lens<>(Person::address, (p, a) -> new Person(p.name(), a));
    static final Lens<Address, String> city = new Lens<>(Address::city, (a, c) -> new Address(c, a.zip()));
    static final Lens<Person, String> personCity = address.andThen(city);
}
