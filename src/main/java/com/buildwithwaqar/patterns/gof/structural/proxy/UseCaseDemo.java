package com.buildwithwaqar.patterns.gof.structural.proxy;
import com.buildwithwaqar.patterns.gof.structural.proxy.LazyImageProxy;
/** Problem: gallery page must not load every image from disk until displayed. */
public final class UseCaseDemo {
  public static void main(String[] a) {
    System.out.println("=== Proxy problem: lazy image load ===");
    var img = new LazyImageProxy("/cdn/hero.png");
    System.out.println("loadedBeforeScroll=" + img.loaded());
    System.out.println(img.display());
    System.out.println("loadedAfter=" + img.loaded());
  }
}
