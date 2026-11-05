package com.buildwithwaqar.patterns.functional.tagless;

import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaglessFinalTest {
    @Test void interpretsAgainstOption() {
        var console = new TaglessFinal.OptionConsole("Waqar");
        var result = TaglessFinal.greet(OptionKind.MONAD, console);
        assertEquals("Waqar", OptionKind.lower(result).get());
    }
}
