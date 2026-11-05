package com.buildwithwaqar.patterns.enterprise.nullobject;

interface Logger {
    void info(String msg);
}

final class ConsoleLogger implements Logger {
    private final StringBuilder sink;
    ConsoleLogger(StringBuilder sink) { this.sink = sink; }
    public void info(String msg) { sink.append(msg).append('\n'); }
}

final class NullLogger implements Logger {
    static final NullLogger INSTANCE = new NullLogger();
    public void info(String msg) { /* no-op */ }
}

/** Prefer Null Object over null checks at call sites. */
public final class OrderService {
    private final Logger logger;

    public OrderService(Logger logger) {
        this.logger = logger == null ? NullLogger.INSTANCE : logger;
    }

    public String place(String id) {
        logger.info("placing " + id);
        return "ok:" + id;
    }
}
