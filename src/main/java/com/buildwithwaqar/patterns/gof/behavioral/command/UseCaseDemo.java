package com.buildwithwaqar.patterns.gof.behavioral.command;

import com.buildwithwaqar.patterns.gof.behavioral.command.*;
/** Problem: text editor needs undoable typing. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Command problem: undoable editor ===");
    var ed = new Editor(); var bus = new CommandBus();
    bus.run(new WriteCommand(ed, "Hello "));
    bus.run(new WriteCommand(ed, "Waqar"));
    System.out.println("text=" + ed.text());
    bus.undo();
    System.out.println("undo=" + ed.text());
  }
}
