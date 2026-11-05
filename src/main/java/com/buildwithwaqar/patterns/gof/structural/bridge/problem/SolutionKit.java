package com.buildwithwaqar.patterns.gof.structural.bridge.problem;

import java.util.*;

interface Renderer { String draw(String shape); }
record VectorRenderer() implements Renderer { public String draw(String s){ return "vector-"+s; } }
record RasterRenderer() implements Renderer { public String draw(String s){ return "raster-"+s; } }
abstract class Shape {
  protected final Renderer r; protected Shape(Renderer r){ this.r=r; }
  public abstract String draw();
}
final class Circle extends Shape {
  private final int radius; public Circle(Renderer r, int radius){ super(r); this.radius=radius; }
  public String draw(){ return r.draw("circle r="+radius); }
}
final class Square extends Shape {
  private final int side; public Square(Renderer r, int side){ super(r); this.side=side; }
  public String draw(){ return r.draw("square s="+side); }
}
