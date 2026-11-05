package com.buildwithwaqar.patterns.functional.collections;

import com.buildwithwaqar.patterns.functional.collections.ZazrCollectionsDemo;
import dev.zazr.collection.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void persists() {
    var xs = Vector.of(1,2);
    assertEquals(Vector.of(2,4), ZazrCollectionsDemo.doubleAll(xs));
    assertEquals(Vector.of(1,2), xs);
  }
}
