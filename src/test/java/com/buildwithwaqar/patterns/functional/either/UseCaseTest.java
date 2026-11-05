package com.buildwithwaqar.patterns.functional.either;

import com.buildwithwaqar.patterns.functional.either.Railway;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void okAndErr() {
    assertTrue(Railway.explain(Railway.label("2")).startsWith("ok:"));
    assertTrue(Railway.explain(Railway.label("x")).startsWith("err:"));
  }
}
