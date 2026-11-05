package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Flyweight: share glyph styles ===");
    System.out.println("--- naive ---");
System.out.println("stylesCreated="+NaiveGlyphs.render(1000));
System.out.println("--- flyweight ---");
var f=new GlyphFactory();
for (int i=0;i<1000;i++) new Glyph((char)('a'+(i%26)), f.style("Inter", 12));
System.out.println("cachedStyles="+f.cached());
  }
}
