package com.buildwithwaqar.patterns.enterprise.outbox.problem;
import org.junit.jupiter.api.Test; import java.util.*; import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void relay(){ var s=new OutboxStore(); s.placeOrder("o","P"); var r=new OutboxRelay(s); assertEquals(1,r.relayOnce()); assertEquals(0,r.relayOnce()); assertEquals(List.of("P"), r.published()); } }
