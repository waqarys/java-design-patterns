package com.buildwithwaqar.patterns.concurrency.producerconsumer;

import java.util.ArrayDeque;
import java.util.Queue;

public final class BoundedQueue<T> {
    private final Queue<T> q = new ArrayDeque<>();
    private final int capacity;

    public BoundedQueue(int capacity) { this.capacity = capacity; }

    public synchronized void put(T item) throws InterruptedException {
        while (q.size() == capacity) wait();
        q.add(item);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (q.isEmpty()) wait();
        var item = q.remove();
        notifyAll();
        return item;
    }

    public synchronized int size() { return q.size(); }
}
