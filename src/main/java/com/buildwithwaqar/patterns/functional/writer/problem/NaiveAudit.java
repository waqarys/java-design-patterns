package com.buildwithwaqar.patterns.functional.writer.problem;

import java.util.*;

public final class NaiveAudit {
  public static int total(List<Integer> xs){ return xs.stream().mapToInt(i->i).sum(); }
}
