package com.buildwithwaqar.patterns.functional.typeclass;

import com.buildwithwaqar.patterns.functional.typeclass.*;
import dev.zazr.control.Option;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void maps() {
    assertEquals(Option.some(2), OptionKind.lower(OptionKind.MONAD.map(OptionKind.lift(Option.some(1)), x -> x*2)));
  }
}
