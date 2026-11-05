package com.buildwithwaqar.patterns.gof.behavioral.mediator;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MediatorTest {
    @Test void routesWithoutDirectCoupling() {
        var room = new ChatRoom();
        var alice = new User("alice", room);
        var bob = new User("bob", room);
        alice.send("hi");
        assertEquals(List.of("alice: hi"), bob.inbox());
        assertTrue(alice.inbox().isEmpty());
    }
}
