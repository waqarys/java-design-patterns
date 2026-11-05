package com.buildwithwaqar.patterns.functional.cake.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CakeProblemTest {
    @Test void liveRegistersAndWelcomes() {
        var cake = new AppCake.LiveCake();
        assertEquals("201:a", cake.userController().postRegister("a", "a@b.c"));
        assertEquals("200:hello a@b.c", cake.userController().getWelcome("a"));
        assertEquals(1, cake.mailbox().size());
    }

    @Test void duplicateReturns409() {
        var cake = new AppCake.LiveCake();
        cake.userController().postRegister("a", "a@b.c");
        assertTrue(cake.userController().postRegister("a", "a@b.c").startsWith("409:"));
    }

    @Test void testCakeUsesSeedWithoutRealIO() {
        var cake = new AppCake.TestCake().seed(new AppCake.User("s", "s@x"));
        assertEquals("200:hello s@x", cake.userController().getWelcome("s"));
        assertTrue(cake.mailbox().isEmpty());
    }

    @Test void missingUser404() {
        assertTrue(new AppCake.TestCake().userController().getWelcome("no").startsWith("404:"));
    }
}
