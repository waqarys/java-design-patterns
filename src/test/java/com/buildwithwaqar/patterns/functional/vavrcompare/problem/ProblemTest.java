package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
    @Test void describeSomeMatchesAcrossLibraries() {
        assertEquals("vavr-some:x", OptionCompare.vavrDescribe("x"));
        assertEquals("zazr-some:x", OptionCompare.zazrDescribe("x"));
    }

    @Test void describeNoneMatchesAcrossLibraries() {
        assertEquals("vavr-none", OptionCompare.vavrDescribe(null));
        assertEquals("zazr-none", OptionCompare.zazrDescribe(null));
    }

    @Test void flatMapPipelineAgreesOnValue() {
        assertEquals("vavr-some:hello", OptionCompare.vavrMapFlat("  Hello "));
        assertEquals("zazr-some:hello", OptionCompare.zazrMapFlat("  Hello "));
        assertEquals("vavr-none", OptionCompare.vavrMapFlat("   "));
        assertEquals("zazr-none", OptionCompare.zazrMapFlat(null));
    }

    @Test void naiveNullIsAmbiguous() {
        assertEquals("none", NaiveOpt.describe(null));
        assertEquals("some:Hi", NaiveOpt.describe("Hi"));
    }
}
