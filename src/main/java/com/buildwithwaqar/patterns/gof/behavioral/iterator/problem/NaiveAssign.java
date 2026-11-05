package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;

import java.util.*;

public final class NaiveAssign {
  private int i; private final List<String> w;
  public NaiveAssign(List<String> w){ this.w=w; }
  public String next(){ return w.get(i++ % w.size()); }
}
