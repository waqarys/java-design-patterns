package com.buildwithwaqar.patterns.gof.behavioral.templatemethod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TemplateMethodTest {
    @Test void runsSkeleton() {
        assertEquals("rows=2", new CsvMiner().mine("x.csv"));
    }
}
