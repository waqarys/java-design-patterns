package com.buildwithwaqar.patterns.gof.structural.flyweight;
import com.buildwithwaqar.patterns.gof.structural.flyweight.GlyphFactory;
/** Problem: render a document without allocating a style object per character. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Flyweight problem: shared glyph styles ===");
    var f = new GlyphFactory();
    for (int i = 0; i < 1000; i++) f.glyph((char)('A' + (i % 26)), f.style("Inter", 14, "#fff"), i, 0);
    System.out.println("chars=1000 pooledStyles=" + f.pooled());
  }
}
