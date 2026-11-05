package com.buildwithwaqar.patterns.functional.adt;

import com.buildwithwaqar.patterns.functional.adt.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UseCaseTest {
  @Test void emailAndPay() {
    assertTrue(Email.parse("a@b.c").isRight());
    assertTrue(Payment.describe(new CashPayment(1)).contains("cash"));
  }
}
