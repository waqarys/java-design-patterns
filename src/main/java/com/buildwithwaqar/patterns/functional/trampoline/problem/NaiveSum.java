package com.buildwithwaqar.patterns.functional.trampoline.problem;

import java.util.*;

public final class NaiveSum {
  public static long sum(long n){ return n<=0?0: n+sum(n-1); }
}
