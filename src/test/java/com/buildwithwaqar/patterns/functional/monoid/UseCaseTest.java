package com.buildwithwaqar.patterns.functional.monoid;

import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void folds() { assertEquals(6, Monoids.INT_SUM.fold(List.of(1,2,3))); }
}
