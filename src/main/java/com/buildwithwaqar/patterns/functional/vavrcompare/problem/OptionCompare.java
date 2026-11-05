package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

/**
 * Side-by-side: same Option describe / map+flatMap program on real VAVR and real Zazr.
 */
public final class OptionCompare {
    private OptionCompare() {}

    public static String vavrDescribe(String raw) {
        io.vavr.control.Option<String> o = raw == null
                ? io.vavr.control.Option.none()
                : io.vavr.control.Option.some(raw);
        return o.map(s -> "vavr-some:" + s).getOrElse("vavr-none");
    }

    public static String zazrDescribe(String raw) {
        dev.zazr.control.Option<String> o = raw == null
                ? dev.zazr.control.Option.none()
                : dev.zazr.control.Option.some(raw);
        return o.map(s -> "zazr-some:" + s).getOrElse("zazr-none");
    }

    /** Trim → reject blank → lowercase — identical pipeline on both libraries. */
    public static String vavrMapFlat(String raw) {
        return io.vavr.control.Option.of(raw)
                .flatMap(s -> s.isBlank() ? io.vavr.control.Option.none() : io.vavr.control.Option.some(s.trim()))
                .map(String::toLowerCase)
                .map(s -> "vavr-some:" + s)
                .getOrElse("vavr-none");
    }

    public static String zazrMapFlat(String raw) {
        return (raw == null ? dev.zazr.control.Option.<String>none() : dev.zazr.control.Option.some(raw))
                .flatMap(s -> s.isBlank() ? dev.zazr.control.Option.none() : dev.zazr.control.Option.some(s.trim()))
                .map(String::toLowerCase)
                .map(s -> "zazr-some:" + s)
                .getOrElse("zazr-none");
    }
}
