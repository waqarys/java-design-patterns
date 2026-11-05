package com.buildwithwaqar.patterns.gof.behavioral.command;

import com.buildwithwaqar.patterns.gof.behavioral.command.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void undoes() {
    var ed = new Editor(); var bus = new CommandBus();
    bus.run(new WriteCommand(ed, "ab"));
    bus.undo();
    assertEquals("", ed.text());
  }
}
