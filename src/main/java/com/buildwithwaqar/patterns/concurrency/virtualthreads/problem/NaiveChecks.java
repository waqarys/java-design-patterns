package com.buildwithwaqar.patterns.concurrency.virtualthreads.problem;

import java.util.*;

public final class NaiveChecks {
  public static int run(int n){ int ok=0; for(int i=0;i<n;i++) ok++; return ok; }
}
