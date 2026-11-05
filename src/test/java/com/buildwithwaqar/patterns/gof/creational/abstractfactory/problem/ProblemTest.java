package com.buildwithwaqar.patterns.gof.creational.abstractfactory.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void familiesConsistent() {
  assertEquals("light-button+light-checkbox", new Dialog(new LightFactory()).render());
  assertEquals("dark-button+dark-checkbox", new Dialog(new DarkFactory()).render());
}
@Test void naiveMixes() { assertTrue(NaiveUi.render("light").contains("dark-checkbox")); }
}
