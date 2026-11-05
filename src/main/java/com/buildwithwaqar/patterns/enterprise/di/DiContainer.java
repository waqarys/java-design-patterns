package com.buildwithwaqar.patterns.enterprise.di;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/** Tiny DI / service locator for demos — prefer a real container in production. */
public final class DiContainer {
    private final Map<Class<?>, Supplier<?>> factories = new HashMap<>();
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    public <T> void bind(Class<T> type, Supplier<? extends T> factory) {
        factories.put(type, factory);
    }

    public <T> void singleton(Class<T> type, Supplier<? extends T> factory) {
        factories.put(type, () -> singletons.computeIfAbsent(type, k -> factory.get()));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        var f = factories.get(type);
        if (f == null) throw new IllegalArgumentException("unbound " + type.getName());
        return (T) Objects.requireNonNull(f.get());
    }
}

interface Greeter { String greet(String name); }
record FriendlyGreeter() implements Greeter {
    public String greet(String name) { return "hello " + name; }
}
