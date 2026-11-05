package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void nestedSize(){
  var root=new DirNode("r").add(new FileNode("a",3)).add(new DirNode("d").add(new FileNode("b",4)));
  assertEquals(7, root.size());
}
}
