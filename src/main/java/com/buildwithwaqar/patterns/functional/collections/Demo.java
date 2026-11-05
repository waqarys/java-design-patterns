package com.buildwithwaqar.patterns.functional.collections;
import dev.zazr.collection.*;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Persistent Collections walkthrough ===");
    var xs = Vector.of(1,2,3);
    System.out.println("orig=" + xs);
    System.out.println("doubled=" + ZazrCollectionsDemo.doubleAll(xs));
    System.out.println("max=" + ZazrCollectionsDemo.maxScore(NonEmptyVector.of(7,3,9)));
    System.out.println("built=" + ZazrCollectionsDemo.buildFast(5));
  }
}
