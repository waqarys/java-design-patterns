package com.buildwithwaqar.patterns.gof.behavioral.observer.problem;

import java.util.*;

public final class NaivePublish {
  public static void publish(String id){ System.out.println("audit "+id); System.out.println("ui "+id); }
}
