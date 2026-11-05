package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

final class Editor { private final StringBuilder sb=new StringBuilder();
  void write(String s){ sb.append(s); } void deleteLast(int n){ sb.delete(Math.max(0,sb.length()-n), sb.length()); }
  String text(){ return sb.toString(); }
}
interface Command { void execute(); void undo(); }
final class WriteCommand implements Command {
  private final Editor ed; private final String chunk;
  WriteCommand(Editor ed, String chunk){ this.ed=ed; this.chunk=chunk; }
  public void execute(){ ed.write(chunk); } public void undo(){ ed.deleteLast(chunk.length()); }
}
final class CommandBus {
  private final Deque<Command> hist=new ArrayDeque<>();
  void run(Command c){ c.execute(); hist.push(c); }
  void undo(){ if(!hist.isEmpty()) hist.pop().undo(); }
}
