package com.buildwithwaqar.patterns.gof.structural.bridge;

/** Public entry points for the bridge use-case demo. */
public final class BridgeShapes {
    private BridgeShapes() {}
    public static String vectorCircle(int r) { return new Circle(new VectorRenderer(), r).draw(); }
    public static String rasterSquare(int s) { return new Square(new RasterRenderer(), s).draw(); }
}
