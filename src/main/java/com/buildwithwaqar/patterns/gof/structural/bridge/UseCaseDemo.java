package com.buildwithwaqar.patterns.gof.structural.bridge;

/** Problem: support Vector and Raster rendering without exploding Shape×Renderer subclasses. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Bridge problem: shape x renderer ===");
    System.out.println("naive: VectorCircle, RasterCircle, VectorSquare, RasterSquare...");
    System.out.println(BridgeShapes.vectorCircle(3));
    System.out.println(BridgeShapes.rasterSquare(4));
  }
}
