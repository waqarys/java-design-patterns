package com.buildwithwaqar.patterns.gof.behavioral.mediator;

import com.buildwithwaqar.patterns.gof.behavioral.mediator.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void fansOut() {
    var room = new ChatRoom();
    var a = new User("a", room); var b = new User("b", room);
    a.send("x");
    assertEquals(List.of("a: x"), b.inbox());
    assertTrue(a.inbox().isEmpty());
  }
}
