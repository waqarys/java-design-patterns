package com.buildwithwaqar.patterns.functional.typeclass;
import dev.zazr.control.Option;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Typeclass / Monad walkthrough ===");
    var fa = OptionKind.lift(Option.some(21));
    var fb = OptionKind.MONAD.map(fa, n -> n * 2);
    System.out.println("mapped=" + OptionKind.lower(fb));
  }
}
