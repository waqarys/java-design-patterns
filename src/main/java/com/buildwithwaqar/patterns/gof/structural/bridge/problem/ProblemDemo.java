package com.buildwithwaqar.patterns.gof.structural.bridge.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Bridge: shape × renderer ===");
    System.out.println("--- naive ---");
System.out.println(NaiveShapes.vectorCircle(3));
System.out.println("--- bridge ---");
System.out.println(new Circle(new VectorRenderer(), 3).draw());
System.out.println(new Square(new RasterRenderer(), 4).draw());
  }
}
