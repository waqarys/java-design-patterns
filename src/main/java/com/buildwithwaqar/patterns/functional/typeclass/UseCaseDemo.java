package com.buildwithwaqar.patterns.functional.typeclass;

import com.buildwithwaqar.patterns.functional.typeclass.*;
import dev.zazr.control.Option;
/** Problem: map over Option via an explicit Monad dictionary (no HKTs). */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Typeclass/Monad problem: Option dictionary ===");
    var fa = OptionKind.lift(Option.some(21));
    System.out.println(OptionKind.lower(OptionKind.MONAD.map(fa, n -> n * 2)));
  }
}
