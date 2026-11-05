package com.buildwithwaqar.patterns.functional.foldable;

import com.buildwithwaqar.patterns.functional.foldable.Foldable;
import com.buildwithwaqar.patterns.functional.monoid.Monoids;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void fold() { assertEquals(6, Foldable.fold(List.of(1,2,3), Monoids.INT_SUM)); }
}
