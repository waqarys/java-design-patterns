package com.buildwithwaqar.patterns.gof.structural.flyweight;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Flyweight walkthrough ===");
    var f = new GlyphFactory();
    var s1 = f.style("Inter", 14, "#e2e8f0");
    var s2 = f.style("Inter", 14, "#e2e8f0");
    var s3 = f.style("Inter", 18, "#e2e8f0");
    System.out.println("sameStyle=" + (s1 == s2) + " pooled=" + f.pooled());
    System.out.println("glyph=" + f.glyph('W', s1, 10, 20));
    System.out.println("thirdDistinct pooled=" + f.pooled());
  }
}
