package com.buildwithwaqar.patterns.gof.behavioral.chain;

public abstract class SupportHandler {
    private SupportHandler next;

    public SupportHandler linkWith(SupportHandler next) {
        this.next = next;
        return next;
    }

    public final String handle(String request) {
        var result = doHandle(request);
        if (result != null) return result;
        return next != null ? next.handle(request) : "unhandled:" + request;
    }

    protected abstract String doHandle(String request);
}

final class BillingHandler extends SupportHandler {
    protected String doHandle(String request) {
        return request.startsWith("bill:") ? "billing-ok" : null;
    }
}

final class TechHandler extends SupportHandler {
    protected String doHandle(String request) {
        return request.startsWith("tech:") ? "tech-ok" : null;
    }
}

final class GeneralHandler extends SupportHandler {
    protected String doHandle(String request) { return "general-ok"; }
}
