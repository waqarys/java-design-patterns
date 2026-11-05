package com.buildwithwaqar.patterns.functional.collections;

import com.buildwithwaqar.patterns.functional.collections.ZazrCollectionsDemo;
import dev.zazr.collection.*;
/** Problem: edit a playlist immutably while sharing structure. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Persistent Collections problem: playlist edits ===");
    var xs = Vector.of(1,2,3);
    System.out.println("orig=" + xs + " doubled=" + ZazrCollectionsDemo.doubleAll(xs));
    System.out.println("max=" + ZazrCollectionsDemo.maxScore(NonEmptyVector.of(7,3,9)));
  }
}
