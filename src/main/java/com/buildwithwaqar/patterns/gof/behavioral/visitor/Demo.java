package com.buildwithwaqar.patterns.gof.behavioral.visitor;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Visitor walkthrough ===");
    Element c = new CircleEl(2);
    Element r = new RectEl(3, 4);
    System.out.println("circleAreaPM=" + ShapeVisitor.area(c));
    System.out.println("circleAreaVisitor=" + ShapeVisitor.areaViaVisitor(c));
    System.out.println("rectArea=" + ShapeVisitor.area(r));
  }
}
