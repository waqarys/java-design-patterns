package com.buildwithwaqar.patterns.functional.monoid.problem;

import java.util.*;

public final class NaiveSum {
  public static int sum(List<Integer> xs){ int s=0; for(int x:xs)s+=x; return s; }
}
