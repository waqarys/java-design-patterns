package com.buildwithwaqar.patterns.enterprise.repository;

import com.buildwithwaqar.patterns.enterprise.repository.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void saves() {
    var r = new InMemoryUserRepository();
    r.save(new User("1","a@b.c"));
    assertEquals("a@b.c", r.findById("1").orElseThrow().email());
  }
}
