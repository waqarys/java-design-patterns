package com.buildwithwaqar.patterns.functional.state.problem;

import java.util.*;

public final class NaiveRng {
  private int seed; NaiveRng(int s){ seed=s; }
  public int next(){ seed = seed*1103515245+12345; return Math.abs(seed%100); }
}
