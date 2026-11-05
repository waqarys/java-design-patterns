package com.buildwithwaqar.patterns.functional.smartctor.problem;

/** Naive: public constructors / raw strings — invalid money/email/IBAN compile fine. */
public final class NaiveValues {
    public record RawMoney(String currency, int cents) {}
    public record RawEmail(String value) {}
    public record RawIban(String value) {}
    public static RawMoney money(String c, int cents) { return new RawMoney(c, cents); } // accepts -1
    public static RawEmail email(String v) { return new RawEmail(v); } // accepts "not-an-email"
    public static RawIban iban(String v) { return new RawIban(v); }
}
