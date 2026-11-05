package com.buildwithwaqar.patterns.gof.structural.bridge;

interface Renderer { String render(String shape); }

record VectorRenderer() implements Renderer {
    public String render(String shape) { return "vector:" + shape; }
}
record RasterRenderer() implements Renderer {
    public String render(String shape) { return "raster:" + shape; }
}

/** Abstraction decoupled from implementation via composition. */
public abstract class Shape {
    protected final Renderer renderer;
    protected Shape(Renderer renderer) { this.renderer = renderer; }
    public abstract String draw();
}

final class Circle extends Shape {
    private final int radius;
    Circle(Renderer renderer, int radius) { super(renderer); this.radius = radius; }
    public String draw() { return renderer.render("circle(" + radius + ")"); }
}

final class Square extends Shape {
    private final int side;
    Square(Renderer renderer, int side) { super(renderer); this.side = side; }
    public String draw() { return renderer.render("square(" + side + ")"); }
}
