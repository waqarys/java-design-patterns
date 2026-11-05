package com.buildwithwaqar.patterns.gof.creational.singleton.problem;

/** Naive: each service constructs its own counter — totals disagree. */
public final class NaiveMetrics {
    private long hits;
    public void hit() { hits++; }
    public long hits() { return hits; }
}
