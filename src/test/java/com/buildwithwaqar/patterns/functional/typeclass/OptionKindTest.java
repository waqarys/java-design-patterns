package com.buildwithwaqar.patterns.functional.typeclass;

import dev.zazr.control.Option;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OptionKindTest {
    @Test void monadMap() {
        var fa = OptionKind.lift(Option.some(21));
        var fb = OptionKind.MONAD.map(fa, n -> n * 2);
        assertEquals(Option.some(42), OptionKind.lower(fb));
    }
}
