package com.buildwithwaqar.patterns.functional.reader.problem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReaderProblemTest {
    private FileSystem.InMemory sampleFs() {
        return new FileSystem.InMemory()
                .put("config.yaml", "app=demo\nregion=local\n")
                .put("secrets.env", "TOKEN=abc\n")
                .put("feature-flags.json", "{\n  x: 1\n}\n");
    }

    @Test void readerMergesThreeFilesThroughSharedEnv() {
        var env = Env.inMemory(sampleFs());
        var bundle = ReaderConfigLoader.loadBundle().run(env);
        assertEquals("demo", bundle.require("app"));
        assertEquals("abc", bundle.require("TOKEN"));
        assertEquals("1", bundle.require("x"));
        assertEquals(3, env.log().size());
        assertTrue(env.log().get(0).contains("config.yaml"));
    }

    @Test void missingFileFailsFast() {
        var fs = new FileSystem.InMemory().put("config.yaml", "a=1\n");
        var env = Env.inMemory(fs);
        assertThrows(IllegalStateException.class, () -> ReaderConfigLoader.loadBundle().run(env));
    }

    @Test void naiveAlsoLoadsButDrillsParameters() {
        var bundle = new NaiveMultiFileLoader(sampleFs(), "/x").load();
        assertEquals("demo", bundle.require("app"));
    }
}
