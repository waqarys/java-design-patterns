package com.buildwithwaqar.patterns.functional.tagless.problem;

/** Problem: same registration program under IO-style and pure test interpreters. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Tagless Final: user registration algebras ===");
        System.out.println("--- naive (hard-wired Map + System.out) ---");
        var naive = new NaiveRegistration();
        System.out.println(naive.register("u1", "a@b.c"));
        System.out.println("outbox=" + naive.outbox());

        System.out.println("--- IO-style interpreter ---");
        var repo = new Algebras.IoUserRepo();
        var mail = new Algebras.IoEmail();
        var log = new Algebras.IoLogger();
        var user = Algebras.register(Algebras.IO, repo, mail, log, "u2", "x@y.z").unsafeRun();
        System.out.println("user=" + user);
        System.out.println("emails=" + mail.sent());
        System.out.println("logs=" + log.lines());

        System.out.println("--- pure test interpreter (shared state) ---");
        var state = Algebras.TestState.empty();
        var prog = Algebras.register(
                Algebras.TEST, new Algebras.TestUserRepo(), new Algebras.TestEmail(), new Algebras.TestLogger(),
                "u3", "t@est.io");
        var saved = ((Algebras.Test<Algebras.User>) prog).runWith(state);
        System.out.println("user=" + saved);
        System.out.println("emails=" + state.emails());
        System.out.println("logs=" + state.logs());
        System.out.println("db=" + state.users().keySet());
    }
}
