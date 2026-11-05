package com.buildwithwaqar.patterns.gof.behavioral.visitor;

sealed interface Element permits CircleEl, RectEl {
    <R> R accept(Visitor<R> visitor);
}

record CircleEl(double r) implements Element {
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}

record RectEl(double w, double h) implements Element {
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}

interface Visitor<R> {
    R visit(CircleEl c);
    R visit(RectEl r);
}

/** Visitor vs Java 25 pattern matching on sealed types. */
public final class ShapeVisitor {
    private ShapeVisitor() {}

    public static double area(Element e) {
        return switch (e) {
            case CircleEl(var r) -> Math.PI * r * r;
            case RectEl(var w, var h) -> w * h;
        };
    }

    public static double areaViaVisitor(Element e) {
        return e.accept(new Visitor<>() {
            public Double visit(CircleEl c) { return Math.PI * c.r() * c.r(); }
            public Double visit(RectEl r) { return r.w() * r.h(); }
        });
    }
}
