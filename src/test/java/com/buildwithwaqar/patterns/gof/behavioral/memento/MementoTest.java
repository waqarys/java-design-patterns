package com.buildwithwaqar.patterns.gof.behavioral.memento;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MementoTest {
    @Test void restoresSnapshot() {
        var editor = new TextEditor();
        var history = new TextEditor.History();
        editor.type("Hello");
        history.push(editor.save());
        editor.type(" World");
        assertEquals("Hello World", editor.text());
        editor.restore(history.pop());
        assertEquals("Hello", editor.text());
    }
}
