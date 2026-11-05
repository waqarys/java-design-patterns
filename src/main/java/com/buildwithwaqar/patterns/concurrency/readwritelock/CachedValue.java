package com.buildwithwaqar.patterns.concurrency.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public final class CachedValue<T> {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private T value;

    public T get(Supplier<T> loader) {
        lock.readLock().lock();
        try {
            if (value != null) return value;
        } finally {
            lock.readLock().unlock();
        }
        lock.writeLock().lock();
        try {
            if (value == null) value = loader.get();
            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clear() {
        lock.writeLock().lock();
        try { value = null; } finally { lock.writeLock().unlock(); }
    }
}
