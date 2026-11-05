package com.buildwithwaqar.patterns.functional.free;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FreeTest {
    @Test void interpretsProgram() {
        Free<String> program = Free.printLine("name?")
                .flatMap(ignored -> Free.readLine())
                .map(n -> "hi " + n);
        var out = new StringBuilder();
        assertEquals("hi Waqar", Free.interpret(program, "Waqar", out));
        assertTrue(out.toString().contains("name?"));
    }
}
