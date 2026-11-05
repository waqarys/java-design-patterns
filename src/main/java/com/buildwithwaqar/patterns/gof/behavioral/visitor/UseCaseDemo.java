package com.buildwithwaqar.patterns.gof.behavioral.visitor;

import com.buildwithwaqar.patterns.gof.behavioral.visitor.*;
/** Problem: compute shape areas without modifying shape classes (or use sealed switch). */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Visitor problem: shape areas ===");
    Element c = new CircleEl(2);
    System.out.println("pm=" + ShapeVisitor.area(c));
    System.out.println("visitor=" + ShapeVisitor.areaViaVisitor(c));
  }
}
