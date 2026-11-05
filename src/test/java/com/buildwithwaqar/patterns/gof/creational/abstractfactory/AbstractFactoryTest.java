package com.buildwithwaqar.patterns.gof.creational.abstractfactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractFactoryTest {
    @Test void lightFamilyIsConsistent() {
        var d = new Dialog(UiFactory.of(Theme.LIGHT));
        assertEquals("light-button+light-checkbox", d.render());
    }

    @Test void darkFamilyIsConsistent() {
        var d = new Dialog(UiFactory.of(Theme.DARK));
        assertEquals("dark-button+dark-checkbox", d.render());
    }
}
