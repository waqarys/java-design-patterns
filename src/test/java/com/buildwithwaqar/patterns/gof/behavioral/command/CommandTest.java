package com.buildwithwaqar.patterns.gof.behavioral.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    @Test void undoRestoresState() {
        var editor = new Editor();
        var bus = new CommandBus();
        bus.run(new WriteCommand(editor, "Hi"));
        bus.run(new WriteCommand(editor, "!"));
        assertEquals("Hi!", editor.text());
        bus.undo();
        assertEquals("Hi", editor.text());
    }
}
