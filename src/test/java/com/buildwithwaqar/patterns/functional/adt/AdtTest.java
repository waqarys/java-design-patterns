package com.buildwithwaqar.patterns.functional.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdtTest {
    @Test void smartConstructor() {
        assertTrue(Email.parse("a@b.c").isRight());
        assertTrue(Email.parse("nope").isLeft());
        assertThrows(IllegalArgumentException.class, () -> new Email("nope"));
    }

    @Test void sealedPayment() {
        assertEquals("cash 100", Payment.describe(new CashPayment(100)));
        assertEquals("card ****1234 500", Payment.describe(new CardPayment(500, "1234")));
    }
}
