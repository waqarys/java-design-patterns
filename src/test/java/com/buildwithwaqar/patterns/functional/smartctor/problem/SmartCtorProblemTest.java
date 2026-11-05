package com.buildwithwaqar.patterns.functional.smartctor.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SmartCtorProblemTest {
    @Test void validMoney() {
        assertTrue(ValueObjects.Money.of("usd", 0).isPresent());
    }
    @Test void rejectsNegativeMoneyAndShortCurrency() {
        assertTrue(ValueObjects.Money.of("USD", -1).isEmpty());
        assertTrue(ValueObjects.Money.of("US", 1).isEmpty());
    }
    @Test void emailNormalized() {
        assertEquals("a@b.c", ValueObjects.Email.of("A@B.C").orElseThrow().value());
    }
    @Test void rejectsBadEmailAndIban() {
        assertTrue(ValueObjects.Email.of("nope").isEmpty());
        assertTrue(ValueObjects.Iban.of("12").isEmpty());
    }
    @Test void naiveAllowsBad() {
        assertEquals(-5, NaiveValues.money("US", -5).cents());
    }
}
