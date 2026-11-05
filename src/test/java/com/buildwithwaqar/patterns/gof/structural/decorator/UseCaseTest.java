package com.buildwithwaqar.patterns.gof.structural.decorator;
import com.buildwithwaqar.patterns.gof.structural.decorator.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void roundTrips() {
    DataSource ds = DataSourceDecorators.encryptedCompressedMemory();
    ds.write("a b c");
    assertEquals("abc", ds.read());
  }
}
