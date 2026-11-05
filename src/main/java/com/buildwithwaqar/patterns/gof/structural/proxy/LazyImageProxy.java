package com.buildwithwaqar.patterns.gof.structural.proxy;

interface Image {
    String display();
}

final class RealImage implements Image {
    private final String path;
    RealImage(String path) { this.path = path; }
    public String display() { return "showing:" + path; }
}

/** Virtual proxy — lazy-loads the real subject. */
public final class LazyImageProxy implements Image {
    private final String path;
    private RealImage real;

    public LazyImageProxy(String path) { this.path = path; }

    @Override
    public synchronized String display() {
        if (real == null) real = new RealImage(path);
        return real.display();
    }

    boolean loaded() { return real != null; }
}
