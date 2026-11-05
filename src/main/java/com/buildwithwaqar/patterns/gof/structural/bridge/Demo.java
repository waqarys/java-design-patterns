package com.buildwithwaqar.patterns.gof.structural.bridge;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Bridge walkthrough ===");
    System.out.println(new Circle(new VectorRenderer(), 3).draw());
    System.out.println(new Square(new RasterRenderer(), 4).draw());
    System.out.println(new Circle(new RasterRenderer(), 2).draw());
  }
}
