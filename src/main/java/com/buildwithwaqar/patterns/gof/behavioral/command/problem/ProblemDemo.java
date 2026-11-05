package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Command: undoable editor ===");
    System.out.println("--- naive ---");
var n=new NaiveEditor(); n.write("Hi"); n.undo(); System.out.println(n.text());
System.out.println("--- command ---");
var ed=new Editor(); var bus=new CommandBus();
bus.run(new WriteCommand(ed,"Hello")); bus.run(new WriteCommand(ed,"!"));
System.out.println(ed.text()); bus.undo(); System.out.println("afterUndo="+ed.text());
  }
}
