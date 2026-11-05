package com.buildwithwaqar.patterns.concurrency.activeobject.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void serial() throws Exception { try(var a=new ActiveLedger()){ a.deposit(1).join(); a.deposit(2).join(); assertEquals(3, a.balance().join()); } } }
