package com.buildwithwaqar.patterns.enterprise.outbox.problem;

import java.util.*;

public final class NaiveOrderPub {
  public static void place(String id){ /* save order */ System.out.println("saved "+id); /* publish may fail after */ throw new RuntimeException("broker down"); }
}
