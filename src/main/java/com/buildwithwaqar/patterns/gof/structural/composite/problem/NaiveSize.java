package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

public final class NaiveSize {
  public static int total(Map<String,Integer> flat){ return flat.values().stream().mapToInt(i->i).sum(); }
}
