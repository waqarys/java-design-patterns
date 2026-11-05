package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.function.Supplier;

public final class NaivePublish {
  public static String once(Supplier<String> s){ return s.get(); }
}
