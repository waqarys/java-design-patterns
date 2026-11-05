package com.buildwithwaqar.patterns.enterprise.di;

import com.buildwithwaqar.patterns.enterprise.di.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void singleton() {
    var c = new DiContainer();
    c.singleton(Greeter.class, FriendlyGreeter::new);
    assertSame(c.get(Greeter.class), c.get(Greeter.class));
  }
}
