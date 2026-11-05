package com.buildwithwaqar.patterns.gof.structural.composite;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Composite walkthrough ===");
    var root = new DirectoryComposite("root")
      .add(new FileLeaf("readme.md", 120))
      .add(new DirectoryComposite("src").add(new FileLeaf("App.java", 800)).add(new FileLeaf("Demo.java", 400)));
    System.out.println("rootSize=" + root.size());
    System.out.println("rootName=" + root.name());
  }
}
