package com.buildwithwaqar.patterns.gof.behavioral.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Custom iterator that cycles through a non-empty list. */
public final class RoundRobin<T> implements Iterable<T> {
    private final List<T> items;

    public RoundRobin(List<T> items) {
        if (items.isEmpty()) throw new IllegalArgumentException("empty");
        this.items = List.copyOf(items);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int i = 0;
            public boolean hasNext() { return true; }
            public T next() {
                if (items.isEmpty()) throw new NoSuchElementException();
                var v = items.get(i % items.size());
                i++;
                return v;
            }
        };
    }
}
