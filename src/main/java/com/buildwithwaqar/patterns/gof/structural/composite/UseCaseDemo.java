package com.buildwithwaqar.patterns.gof.structural.composite;
import com.buildwithwaqar.patterns.gof.structural.composite.*;
/** Problem: total size of nested project directories. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Composite problem: folder size ===");
    var root = new DirectoryComposite("repo")
      .add(new FileLeaf("README.md", 100))
      .add(new DirectoryComposite("src").add(new FileLeaf("Main.java", 500)).add(new FileLeaf("Demo.java", 200)));
    System.out.println("totalBytes=" + root.size());
  }
}
