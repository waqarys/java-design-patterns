package com.buildwithwaqar.patterns.gof.creational.abstractfactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractFactoryProblemTest {
    @Test void familiesStayConsistent() {
        assertEquals("light-button+light-checkbox", new Dialog(UiFactory.of(Theme.LIGHT)).render());
        assertEquals("dark-button+dark-checkbox", new Dialog(UiFactory.of(Theme.DARK)).render());
    }
}
