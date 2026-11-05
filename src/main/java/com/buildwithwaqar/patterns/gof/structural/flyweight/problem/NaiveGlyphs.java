package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;

public final class NaiveGlyphs {
  public static int render(int n){
    int styles=0; for(int i=0;i<n;i++) styles++; // new style each glyph
    return styles;
  }
}
