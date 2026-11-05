package com.buildwithwaqar.patterns.gof.structural.proxy;
public final class Demo {
  public static void main(String[] a) {
    System.out.println("=== Proxy walkthrough ===");
    var proxy = new LazyImageProxy("/assets/thumb.png");
    System.out.println("loadedBefore=" + proxy.loaded());
    System.out.println(proxy.display());
    System.out.println("loadedAfter=" + proxy.loaded());
    System.out.println(proxy.display());
  }
}
