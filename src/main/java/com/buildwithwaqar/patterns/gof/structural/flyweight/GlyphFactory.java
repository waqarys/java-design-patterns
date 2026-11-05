package com.buildwithwaqar.patterns.gof.structural.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

record GlyphStyle(String font, int size, String color) {}

record Glyph(char character, GlyphStyle style, int x, int y) {}

/** Flyweight factory pools intrinsic glyph styles. */
public final class GlyphFactory {
    private final Map<GlyphStyle, GlyphStyle> pool = new ConcurrentHashMap<>();

    public GlyphStyle style(String font, int size, String color) {
        var key = new GlyphStyle(font, size, color);
        return pool.computeIfAbsent(key, k -> k);
    }

    public Glyph glyph(char c, GlyphStyle style, int x, int y) {
        return new Glyph(c, style, x, y);
    }

    public int pooled() { return pool.size(); }
}
