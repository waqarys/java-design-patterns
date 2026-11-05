package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class ProblemTest {
  @Test void factoriesProduceChannels() {
    assertTrue(new EmailFactory().notifyUser("a","b").startsWith("email:"));
    assertTrue(new SmsFactory().notifyUser("a","b").startsWith("sms:"));
  }
  @Test void naiveRejectsUnknown() {
    assertThrows(IllegalArgumentException.class, () -> NaiveNotify.send("fax","a","b"));
  }
}
