package com.buildwithwaqar.patterns.concurrency.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/** Educational pool — prefer Executors / virtual threads in real code. */
public final class SimpleThreadPool implements AutoCloseable {
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Thread[] workers;
    private final AtomicBoolean open = new AtomicBoolean(true);

    public SimpleThreadPool(int size) {
        workers = new Thread[size];
        for (int i = 0; i < size; i++) {
            workers[i] = Thread.ofVirtual().name("worker-" + i).start(() -> {
                while (open.get() || !queue.isEmpty()) {
                    try {
                        var task = queue.poll(50, java.util.concurrent.TimeUnit.MILLISECONDS);
                        if (task != null) task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            });
        }
    }

    public void submit(Runnable task) {
        if (!open.get()) throw new IllegalStateException("closed");
        queue.add(task);
    }

    @Override
    public void close() {
        open.set(false);
        for (var w : workers) {
            try { w.join(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}
