package com.buildwithwaqar.patterns.functional.lens;

import com.buildwithwaqar.patterns.functional.lens.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void updates() {
    var p = new Person("A", new Address("L", "1"));
    assertEquals("P", PersonLenses.personCity.modify(p, x -> "P").address().city());
    assertEquals("L", p.address().city());
  }
}
