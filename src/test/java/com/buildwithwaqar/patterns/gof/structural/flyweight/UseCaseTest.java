package com.buildwithwaqar.patterns.gof.structural.flyweight;
import com.buildwithwaqar.patterns.gof.structural.flyweight.GlyphFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void poolsStyles() {
    var f = new GlyphFactory();
    assertSame(f.style("I", 12, "#0"), f.style("I", 12, "#0"));
    assertEquals(1, f.pooled());
  }
}
