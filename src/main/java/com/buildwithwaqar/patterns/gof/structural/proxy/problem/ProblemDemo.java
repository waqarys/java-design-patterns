package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Proxy: lazy-load heavy image ===");
    System.out.println("--- naive (loads immediately) ---");
var n=new NaiveImage("/big.png"); System.out.println(n.display());
System.out.println("--- proxy ---");
Image p=new LazyImageProxy("/big.png");
System.out.println("loadedBefore="+p.loaded());
System.out.println(p.display());
System.out.println("loadedAfter="+p.loaded());
  }
}
