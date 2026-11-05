package com.buildwithwaqar.patterns.functional.kleisli;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import dev.zazr.control.Option;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Kleisli walkthrough ===");
    Kleisli<OptionKind.Witness, String, Integer> parse = Kleisli.of(s -> {
      try { return OptionKind.lift(Option.some(Integer.parseInt(s))); }
      catch (Exception e) { return OptionKind.lift(Option.none()); }
    });
    Kleisli<OptionKind.Witness, Integer, Integer> twice = Kleisli.of(n -> OptionKind.lift(Option.some(n * 2)));
    var pipeline = parse.andThen(OptionKind.MONAD, twice);
    System.out.println("ok=" + OptionKind.lower(pipeline.apply("21")));
    System.out.println("bad=" + OptionKind.lower(pipeline.apply("x")));
  }
}
