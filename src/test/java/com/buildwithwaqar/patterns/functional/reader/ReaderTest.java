package com.buildwithwaqar.patterns.functional.reader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    @Test void readsEnvironment() {
        Reader<Config, String> program = Reader.<Config>ask()
                .flatMap(cfg -> Reader.pure(cfg.greeting() + " " + cfg.name()));
        assertEquals("hi waqar", program.run(new Config("hi", "waqar")));
    }
}
