package com.buildwithwaqar.patterns.gof.creational.singleton;

import com.buildwithwaqar.patterns.gof.creational.singleton.problem.NaiveMetrics;

/** Problem: one MetricsRegistry across request threads (vs split-brain counters). */
public final class UseCaseDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton problem: one metrics registry ===");
        var a = new NaiveMetrics();
        var b = new NaiveMetrics();
        a.hit(); a.hit(); b.hit();
        System.out.println("naiveA=" + a.hits() + " naiveB=" + b.hits() + " (split brain)");

        MetricsRegistry.getInstance().reset();
        MetricsRegistry.getInstance().hit();
        MetricsRegistry.getInstance().hit();
        MetricsRegistry.getInstance().hit();
        System.out.println("singletonHits=" + MetricsRegistry.getInstance().hits());
        System.out.println("same=" + (MetricsRegistry.getInstance() == MetricsRegistry.getInstance()));
    }
}
