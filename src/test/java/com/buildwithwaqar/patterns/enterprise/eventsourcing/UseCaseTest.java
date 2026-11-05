package com.buildwithwaqar.patterns.enterprise.eventsourcing;

import com.buildwithwaqar.patterns.enterprise.eventsourcing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void projects() {
    var a = new Account(); a.deposit(10); a.withdraw(3);
    var v = new BalanceView(); a.events().forEach(v::project);
    assertEquals(7, v.balance());
  }
}
