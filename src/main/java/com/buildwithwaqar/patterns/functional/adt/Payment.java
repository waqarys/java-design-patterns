package com.buildwithwaqar.patterns.functional.adt;

/** Algebraic data type via sealed records + pattern matching. */
public sealed interface Payment permits CardPayment, CashPayment, WirePayment {
    int amountCents();

    static String describe(Payment p) {
        return switch (p) {
            case CardPayment(var cents, var last4) -> "card ****" + last4 + " " + cents;
            case CashPayment(var cents) -> "cash " + cents;
            case WirePayment(var cents, var iban) -> "wire " + iban + " " + cents;
        };
    }
}

record CardPayment(int amountCents, String last4) implements Payment {}
record CashPayment(int amountCents) implements Payment {}
record WirePayment(int amountCents, String iban) implements Payment {}
