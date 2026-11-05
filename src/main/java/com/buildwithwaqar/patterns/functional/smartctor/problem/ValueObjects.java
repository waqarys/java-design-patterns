package com.buildwithwaqar.patterns.functional.smartctor.problem;

import java.util.Optional;

/** Smart constructors: Money, Email, IBAN — invalid values cannot be constructed. */
public final class ValueObjects {
    private ValueObjects() {}

    public static final class Money {
        private final String currency;
        private final int cents;
        private Money(String currency, int cents) {
            this.currency = currency;
            this.cents = cents;
        }
        public String currency() { return currency; }
        public int cents() { return cents; }
        public static Optional<Money> of(String currency, int cents) {
            if (currency == null || currency.length() != 3) return Optional.empty();
            if (cents < 0) return Optional.empty();
            return Optional.of(new Money(currency.toUpperCase(), cents));
        }
        public String toString() { return currency + ":" + cents; }
    }

    public static final class Email {
        private final String value;
        private Email(String value) { this.value = value; }
        public String value() { return value; }
        public static Optional<Email> of(String raw) {
            if (raw == null) return Optional.empty();
            String s = raw.trim();
            int at = s.indexOf('@');
            if (at <= 0 || at != s.lastIndexOf('@') || at == s.length() - 1) return Optional.empty();
            if (s.contains(" ")) return Optional.empty();
            return Optional.of(new Email(s.toLowerCase()));
        }
        public String toString() { return value; }
    }

    public static final class Iban {
        private final String value;
        private Iban(String value) { this.value = value; }
        public String value() { return value; }
        public static Optional<Iban> of(String raw) {
            if (raw == null) return Optional.empty();
            String s = raw.replace(" ", "").toUpperCase();
            if (s.length() < 15 || s.length() > 34) return Optional.empty();
            if (!s.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]+")) return Optional.empty();
            return Optional.of(new Iban(s));
        }
        public String toString() { return value; }
    }
}
