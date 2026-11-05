package com.buildwithwaqar.patterns.gof.structural.decorator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {
    @Test void stacksCompressionAndEncryption() {
        DataSource ds = DataSourceDecorators.encryptedCompressedMemory();
        ds.write("a b");
        assertEquals("ab", ds.read());
    }
}
