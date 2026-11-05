package com.buildwithwaqar.patterns.concurrency.threadpool.problem;

import java.util.*;

public final class NaiveThumbs {
  public static int run(int n){ int c=0; for(int i=0;i<n;i++) c++; return c; } // unbounded threads in real life
}
