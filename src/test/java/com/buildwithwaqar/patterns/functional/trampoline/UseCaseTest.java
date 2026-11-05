package com.buildwithwaqar.patterns.functional.trampoline;

import com.buildwithwaqar.patterns.functional.trampoline.Trampoline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void safe() { assertEquals(15, Trampoline.safeSum(5, 0).run()); }
}
