package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class DocMemento { final String text; DocMemento(String t){ text=t; } }
final class Doc {
  private String text="";
  void write(String s){ text+=s; }
  String text(){ return text; }
  DocMemento save(){ return new DocMemento(text); }
  void restore(DocMemento m){ text=m.text; }
}
final class History {
  private final Deque<DocMemento> stack=new ArrayDeque<>();
  void push(DocMemento m){ stack.push(m); }
  DocMemento pop(){ if(stack.isEmpty()) throw new IllegalStateException("empty"); return stack.pop(); }
}
