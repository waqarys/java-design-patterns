package com.buildwithwaqar.patterns.functional.cake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CakePatternTest {
    @Test void assemblesComponents() {
        var app = new CakePattern.App();
        assertEquals("sent:42@example.com:welcome 42", app.userService().welcome("42"));
    }
}
