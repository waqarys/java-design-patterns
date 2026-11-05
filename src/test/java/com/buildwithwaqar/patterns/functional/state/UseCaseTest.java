package com.buildwithwaqar.patterns.functional.state;

import com.buildwithwaqar.patterns.functional.state.State;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void threads() {
    var r = State.<Integer>get().flatMap(n -> State.<Integer>set(n+1).flatMap(x -> State.pure(n))).run(5);
    assertEquals(5, r.value());
    assertEquals(6, r.state());
  }
}
