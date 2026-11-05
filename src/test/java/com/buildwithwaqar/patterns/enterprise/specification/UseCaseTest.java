package com.buildwithwaqar.patterns.enterprise.specification;

import com.buildwithwaqar.patterns.enterprise.specification.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void and() {
    assertTrue(new PriceSpec(1000).and(new InStockSpec()).test(new Product("m",1,true)));
    assertFalse(new PriceSpec(1000).and(new InStockSpec()).test(new Product("m",1,false)));
  }
}
