package com.buildwithwaqar.patterns.gof.creational.prototype.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void copyIsIndependent(){
  var p=new MeetingTemplate("W", List.of("A"));
  var c=p.copy();
  assertEquals(List.of("A"), c.attendees());
  assertNotSame(p.attendees(), c.attendees());
}
}
