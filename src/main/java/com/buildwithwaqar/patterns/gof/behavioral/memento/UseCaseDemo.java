package com.buildwithwaqar.patterns.gof.behavioral.memento;

import com.buildwithwaqar.patterns.gof.behavioral.memento.TextEditor;
/** Problem: save and restore document checkpoints. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Memento problem: editor checkpoints ===");
    var ed = new TextEditor(); var h = new TextEditor.History();
    ed.type("v1"); h.push(ed.save());
    ed.type("-v2"); System.out.println("now=" + ed.text());
    ed.restore(h.pop()); System.out.println("restored=" + ed.text());
  }
}
