package com.buildwithwaqar.patterns.gof.creational.prototype.problem;

import com.buildwithwaqar.patterns.gof.creational.prototype.DocumentTemplate;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrototypeProblemTest {
    @Test void cloneIsIndependent() {
        var proto = new DocumentTemplate("T", List.of("A"));
        var c = proto.clone();
        c.sections().add("B");
        assertEquals(1, proto.sections().size());
        assertEquals(2, c.sections().size());
    }
}
