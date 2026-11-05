package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Composite: nested folder sizes ===");
    System.out.println("--- naive flat map ---");
System.out.println(NaiveSize.total(Map.of("a.txt",10,"b.txt",20)));
System.out.println("--- composite ---");
var root = new DirNode("src").add(new FileNode("A.java",10))
  .add(new DirNode("util").add(new FileNode("B.java",25)));
System.out.println("size="+root.size());
  }
}
