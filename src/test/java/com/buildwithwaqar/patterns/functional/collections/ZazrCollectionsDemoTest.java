package com.buildwithwaqar.patterns.functional.collections;

import dev.zazr.collection.NonEmptyVector;
import dev.zazr.collection.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZazrCollectionsDemoTest {
    @Test void mapsPersistently() {
        var xs = Vector.of(1, 2, 3);
        assertEquals(Vector.of(2, 4, 6), ZazrCollectionsDemo.doubleAll(xs));
        assertEquals(Vector.of(1, 2, 3), xs);
    }

    @Test void nonEmptyMaxIsTotal() {
        assertEquals(9, ZazrCollectionsDemo.maxScore(NonEmptyVector.of(7, 3, 9)));
    }

    @Test void builder() {
        assertEquals(1000, ZazrCollectionsDemo.buildFast(1000).size());
    }
}
