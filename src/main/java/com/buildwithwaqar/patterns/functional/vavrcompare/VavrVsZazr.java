package com.buildwithwaqar.patterns.functional.vavrcompare;

/** Side-by-side Option usage — VAVR (classic) vs Zazr (Java 25 sealed/records). */
public final class VavrVsZazr {
    private VavrVsZazr() {}

    public static String vavrDescribe(io.vavr.control.Option<Integer> opt) {
        return opt.map(n -> "got " + n).getOrElse("missing");
    }

    public static String zazrDescribe(dev.zazr.control.Option<Integer> opt) {
        return switch (opt) {
            case dev.zazr.control.Option.Some(var n) -> "got " + n;
            case dev.zazr.control.Option.None() -> "missing";
        };
    }
}
