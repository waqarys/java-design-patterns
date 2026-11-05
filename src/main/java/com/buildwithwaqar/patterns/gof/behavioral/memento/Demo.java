package com.buildwithwaqar.patterns.gof.behavioral.memento;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Memento walkthrough ===");
    var editor = new TextEditor();
    var history = new TextEditor.History();
    editor.type("Hello");
    history.push(editor.save());
    editor.type(" World");
    history.push(editor.save());
    editor.type("!!!");
    System.out.println("now=" + editor.text());
    editor.restore(history.pop());
    System.out.println("undo1=" + editor.text());
    editor.restore(history.pop());
    System.out.println("undo2=" + editor.text());
  }
}
