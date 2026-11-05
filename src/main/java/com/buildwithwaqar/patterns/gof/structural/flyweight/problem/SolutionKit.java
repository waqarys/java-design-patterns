package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

final class GlyphStyle {
  private final String font; private final int size;
  GlyphStyle(String font, int size){ this.font=font; this.size=size; }
  String key(){ return font+":"+size; }
}
final class GlyphFactory {
  private final Map<String, GlyphStyle> cache = new HashMap<>();
  GlyphStyle style(String font, int size){
    return cache.computeIfAbsent(font+":"+size, k -> new GlyphStyle(font, size));
  }
  int cached(){ return cache.size(); }
}
final class Glyph {
  private final char c; private final GlyphStyle style;
  Glyph(char c, GlyphStyle style){ this.c=c; this.style=style; }
}
