package com.buildwithwaqar.patterns.gof.creational.singleton.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton: one MetricsRegistry ===");
        var a = new NaiveMetrics(); var b = new NaiveMetrics();
        a.hit(); a.hit(); b.hit();
        System.out.println("naiveA=" + a.hits() + " naiveB=" + b.hits());
        var reg = com.buildwithwaqar.patterns.gof.creational.singleton.MetricsRegistry.getInstance();
        reg.reset(); reg.hit(); reg.hit(); reg.hit();
        System.out.println("singletonHits=" + reg.hits());
        System.out.println("same=" + (reg == com.buildwithwaqar.patterns.gof.creational.singleton.MetricsRegistry.getInstance()));

    }
}
