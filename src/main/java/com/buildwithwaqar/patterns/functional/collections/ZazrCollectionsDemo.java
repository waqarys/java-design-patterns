package com.buildwithwaqar.patterns.functional.collections;

import dev.zazr.collection.NonEmptyVector;
import dev.zazr.collection.Vector;

public final class ZazrCollectionsDemo {
    private ZazrCollectionsDemo() {}

    public static Vector<Integer> doubleAll(Vector<Integer> xs) {
        return xs.map(n -> n * 2);
    }

    public static int maxScore(NonEmptyVector<Integer> scores) {
        return scores.max(Integer::compare);
    }

    public static Vector<Integer> buildFast(int n) {
        var b = Vector.<Integer>newBuilder();
        for (int i = 0; i < n; i++) b.add(i);
        return b.result();
    }
}
