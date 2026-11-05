package com.buildwithwaqar.patterns.functional.smartctor.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Smart Constructors: Money / Email / IBAN ===");
        System.out.println("--- naive allows illegal states ---");
        System.out.println(NaiveValues.money("US", -5));
        System.out.println(NaiveValues.email("not-an-email"));
        System.out.println(NaiveValues.iban("xx"));

        System.out.println("--- smart constructors ---");
        System.out.println("money=" + ValueObjects.Money.of("USD", 1999));
        System.out.println("badMoney=" + ValueObjects.Money.of("US", -1));
        System.out.println("email=" + ValueObjects.Email.of("A@B.C"));
        System.out.println("badEmail=" + ValueObjects.Email.of("nope"));
        System.out.println("iban=" + ValueObjects.Iban.of("DE89 3704 0044 0532 0130 00"));
        System.out.println("badIban=" + ValueObjects.Iban.of("12"));
    }
}
