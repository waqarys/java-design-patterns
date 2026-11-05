package com.buildwithwaqar.patterns.functional.option;

import dev.zazr.control.Option;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZazrOptionDemoTest {
    @Test void parseAndDescribe() {
        assertEquals("got 3", ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("3")));
        assertEquals("missing", ZazrOptionDemo.describe(ZazrOptionDemo.parsePositive("-1")));
    }

    @Test void zipWithArity3() {
        assertEquals(Option.some(6), ZazrOptionDemo.sum3(Option.some(1), Option.some(2), Option.some(3)));
        assertTrue(ZazrOptionDemo.sum3(Option.some(1), Option.none(), Option.some(3)).isEmpty());
    }
}
