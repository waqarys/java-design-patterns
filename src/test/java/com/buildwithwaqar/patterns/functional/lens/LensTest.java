package com.buildwithwaqar.patterns.functional.lens;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LensTest {
    @Test void composesImmutableUpdate() {
        var p = new Person("Ada", new Address("London", "E1"));
        var updated = PersonLenses.personCity.modify(p, c -> "Paris");
        assertEquals("Paris", updated.address().city());
        assertEquals("London", p.address().city());
        assertEquals("Ada", updated.name());
    }
}
