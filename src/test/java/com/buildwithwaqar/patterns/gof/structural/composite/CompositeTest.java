package com.buildwithwaqar.patterns.gof.structural.composite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeTest {
    @Test void directorySumsChildren() {
        var root = new DirectoryComposite("root")
                .add(new FileLeaf("a.txt", 10))
                .add(new DirectoryComposite("sub").add(new FileLeaf("b.txt", 5)));
        assertEquals(15, root.size());
    }
}
