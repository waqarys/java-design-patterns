package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void undo(){ var ed=new Editor(); var bus=new CommandBus(); bus.run(new WriteCommand(ed,"ab")); bus.undo(); assertEquals("", ed.text()); }
}
