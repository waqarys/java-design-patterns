package com.buildwithwaqar.patterns.gof.structural.composite;
import com.buildwithwaqar.patterns.gof.structural.composite.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void sumsNested() {
    assertEquals(9, new DirectoryComposite("r").add(new FileLeaf("a", 4))
      .add(new DirectoryComposite("d").add(new FileLeaf("b", 5))).size());
  }
}
