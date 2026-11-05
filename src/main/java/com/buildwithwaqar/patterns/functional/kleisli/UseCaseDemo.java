package com.buildwithwaqar.patterns.functional.kleisli;

import com.buildwithwaqar.patterns.functional.kleisli.Kleisli;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import dev.zazr.control.Option;
/** Problem: compose parseInt → *2 fallible pipeline. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Kleisli problem: fallible pipeline ===");
    Kleisli<OptionKind.Witness, String, Integer> parse = Kleisli.of(s -> {
      try { return OptionKind.lift(Option.some(Integer.parseInt(s))); }
      catch (Exception e) { return OptionKind.lift(Option.none()); }
    });
    Kleisli<OptionKind.Witness, Integer, Integer> twice = Kleisli.of(n -> OptionKind.lift(Option.some(n * 2)));
    var p = parse.andThen(OptionKind.MONAD, twice);
    System.out.println("ok=" + OptionKind.lower(p.apply("21")));
    System.out.println("bad=" + OptionKind.lower(p.apply("zz")));
  }
}
