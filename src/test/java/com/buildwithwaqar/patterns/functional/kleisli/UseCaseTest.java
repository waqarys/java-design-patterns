package com.buildwithwaqar.patterns.functional.kleisli;

import com.buildwithwaqar.patterns.functional.kleisli.Kleisli;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import dev.zazr.control.Option;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void composes() {
    Kleisli<OptionKind.Witness, String, Integer> parse = Kleisli.of(s -> OptionKind.lift(Option.some(Integer.parseInt(s))));
    Kleisli<OptionKind.Witness, Integer, Integer> inc = Kleisli.of(n -> OptionKind.lift(Option.some(n+1)));
    assertEquals(Option.some(2), OptionKind.lower(parse.andThen(OptionKind.MONAD, inc).apply("1")));
  }
}
