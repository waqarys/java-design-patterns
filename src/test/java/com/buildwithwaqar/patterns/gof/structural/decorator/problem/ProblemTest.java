package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
  @Test void roundTrip(){
  DataSource ds=new EncryptionDecorator(new CompressionDecorator(new InMemoryDataSource()));
  ds.write("hi"); assertEquals("hi", ds.read());
}
}
