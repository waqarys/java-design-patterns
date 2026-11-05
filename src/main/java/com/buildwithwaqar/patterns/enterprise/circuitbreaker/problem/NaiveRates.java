package com.buildwithwaqar.patterns.enterprise.circuitbreaker.problem;

import java.util.*;

public final class NaiveRates {
  private int fails;
  public String rate(){ if(fails++ < 3) throw new RuntimeException("down"); return "83.2"; }
}
