package com.buildwithwaqar.patterns.functional.io.problem;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class EffectProblemTest {
    @Test void prodWritesResult() {
        var fs = new HashMap<String, String>();
        fs.put("/in", "A");
        var prod = new EffectProgram.ProdInterpreter(fs, b -> "R:" + b);
        assertEquals("R:A", prod.run(EffectProgram.reportPipeline("/in", "u", "/out")));
        assertEquals("R:A", fs.get("/out"));
    }

    @Test void testInterpreterCapturesWrites() {
        var test = new EffectProgram.TestInterpreter(Map.of("/in", "B"), "OK", false);
        test.run(EffectProgram.reportPipeline("/in", "u", "/out"));
        assertEquals("OK", test.writes().get("/out"));
    }

    @Test void missingFileFails() {
        var test = new EffectProgram.TestInterpreter(Map.of(), "OK", false);
        assertThrows(IllegalStateException.class,
                () -> test.run(EffectProgram.reportPipeline("/missing", "u", "/out")));
    }

    @Test void httpFailure() {
        var test = new EffectProgram.TestInterpreter(Map.of("/in", "B"), "OK", true);
        assertThrows(IllegalStateException.class,
                () -> test.run(EffectProgram.reportPipeline("/in", "u", "/out")));
    }
}
