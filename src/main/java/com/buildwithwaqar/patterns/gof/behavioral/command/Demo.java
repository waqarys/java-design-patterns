package com.buildwithwaqar.patterns.gof.behavioral.command;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Command walkthrough ===");
    var editor = new Editor();
    var bus = new CommandBus();
    bus.run(new WriteCommand(editor, "Build "));
    bus.run(new WriteCommand(editor, "with "));
    bus.run(new WriteCommand(editor, "Waqar"));
    System.out.println("text=" + editor.text());
    bus.undo();
    System.out.println("afterUndo=" + editor.text());
    bus.undo();
    System.out.println("afterSecondUndo=" + editor.text());
  }
}
