package com.buildwithwaqar.patterns.functional.tagless.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaglessProblemTest {
    @Test void ioInterpreterSendsWelcomeEmail() {
        var repo = new Algebras.IoUserRepo();
        var mail = new Algebras.IoEmail();
        var log = new Algebras.IoLogger();
        var u = Algebras.register(Algebras.IO, repo, mail, log, "a", "a@b.c").unsafeRun();
        assertEquals("a@b.c", u.email());
        assertEquals(1, mail.sent().size());
        assertTrue(mail.sent().get(0).startsWith("a@b.c|Welcome|"));
    }

    @Test void testInterpreterCapturesEmailsWithoutIO() {
        var state = Algebras.TestState.empty();
        var prog = Algebras.register(
                Algebras.TEST, new Algebras.TestUserRepo(), new Algebras.TestEmail(), new Algebras.TestLogger(),
                "b", "b@c.d");
        ((Algebras.Test<Algebras.User>) prog).runWith(state);
        assertEquals(1, state.emails().size());
        assertTrue(state.users().containsKey("b@c.d"));
        assertFalse(state.logs().isEmpty());
    }

    @Test void duplicateEmailReturnsExistingWithoutSecondWelcomeInIo() {
        var repo = new Algebras.IoUserRepo();
        var mail = new Algebras.IoEmail();
        var log = new Algebras.IoLogger();
        Algebras.register(Algebras.IO, repo, mail, log, "a", "dup@x").unsafeRun();
        Algebras.register(Algebras.IO, repo, mail, log, "a2", "dup@x").unsafeRun();
        assertEquals(1, mail.sent().size()); // second path skips send
        assertTrue(log.lines().stream().anyMatch(l -> l.startsWith("duplicate")));
    }
}
