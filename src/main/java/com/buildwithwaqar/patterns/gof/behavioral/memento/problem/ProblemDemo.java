package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Memento: editor snapshots ===");
    System.out.println("--- naive ---");
var n=new NaiveDoc(); n.text="a"; n.save(); n.text="ab"; n.restore(); System.out.println(n.text);
System.out.println("--- memento ---");
var d=new Doc(); var h=new History(); d.write("Hello"); h.push(d.save()); d.write("!");
System.out.println(d.text()); d.restore(h.pop()); System.out.println("restored="+d.text());
  }
}
