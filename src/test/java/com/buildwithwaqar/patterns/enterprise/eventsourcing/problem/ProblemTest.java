package com.buildwithwaqar.patterns.enterprise.eventsourcing.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void ledger(){ var a=new Account(); a.deposit(50); a.withdraw(20); assertEquals(30, a.balance()); assertEquals(2, a.history().size()); }
@Test void overdraftFails(){ var a=new Account(); assertThrows(IllegalStateException.class, ()->a.withdraw(1)); } }
