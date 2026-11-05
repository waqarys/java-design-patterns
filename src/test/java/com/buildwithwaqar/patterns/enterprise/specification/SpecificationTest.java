package com.buildwithwaqar.patterns.enterprise.specification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpecificationTest {
    @Test void composesRules() {
        Specification<Product> cheapInStock = new PriceSpec(1000).and(new InStockSpec());
        assertTrue(cheapInStock.test(new Product("mug", 500, true)));
        assertFalse(cheapInStock.test(new Product("mug", 500, false)));
        assertFalse(cheapInStock.test(new Product("tv", 50000, true)));
    }
}
