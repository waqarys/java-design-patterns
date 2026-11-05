package com.buildwithwaqar.patterns.gof.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class EventBus<T> {
    private final List<Consumer<T>> subscribers = new ArrayList<>();

    public AutoCloseable subscribe(Consumer<T> listener) {
        subscribers.add(listener);
        return () -> subscribers.remove(listener);
    }

    public void publish(T event) {
        for (var s : List.copyOf(subscribers)) s.accept(event);
    }
}
