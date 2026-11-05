package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

/** Problem: same Option describe — real io.vavr vs real dev.zazr. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== VAVR vs Zazr: real Option APIs ===");
        System.out.println("--- naive null String ---");
        System.out.println(NaiveOpt.describe(null));
        System.out.println(NaiveOpt.describe("Hi"));

        System.out.println("--- describe (some/none) ---");
        System.out.println(OptionCompare.vavrDescribe("x"));
        System.out.println(OptionCompare.zazrDescribe("x"));
        System.out.println(OptionCompare.vavrDescribe(null));
        System.out.println(OptionCompare.zazrDescribe(null));

        System.out.println("--- map/flatMap pipeline ---");
        System.out.println(OptionCompare.vavrMapFlat("  Hello "));
        System.out.println(OptionCompare.zazrMapFlat("  Hello "));
        System.out.println(OptionCompare.vavrMapFlat("   "));
        System.out.println(OptionCompare.zazrMapFlat("   "));
        System.out.println(OptionCompare.vavrMapFlat(null));
        System.out.println(OptionCompare.zazrMapFlat(null));
    }
}
