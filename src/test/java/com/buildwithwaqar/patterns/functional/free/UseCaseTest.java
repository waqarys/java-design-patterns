package com.buildwithwaqar.patterns.functional.free;

import com.buildwithwaqar.patterns.functional.free.Free;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void interprets() {
    Free<String> p = Free.printLine("q").flatMap(x -> Free.readLine());
    assertEquals("A", Free.interpret(p, "A", new StringBuilder()));
  }
}
