package com.buildwithwaqar.patterns.gof.behavioral.memento;

import com.buildwithwaqar.patterns.gof.behavioral.memento.TextEditor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void restores() {
    var ed = new TextEditor(); var h = new TextEditor.History();
    ed.type("a"); h.push(ed.save()); ed.type("b"); ed.restore(h.pop());
    assertEquals("a", ed.text());
  }
}
