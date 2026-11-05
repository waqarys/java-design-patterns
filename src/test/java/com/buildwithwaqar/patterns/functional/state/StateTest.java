package com.buildwithwaqar.patterns.functional.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test void threadsState() {
        State<Integer, Integer> program = State.<Integer>get()
                .flatMap(n -> State.<Integer>set(n + 1).flatMap(ignored -> State.pure(n)));
        var r = program.run(10);
        assertEquals(10, r.value());
        assertEquals(11, r.state());
    }
}
