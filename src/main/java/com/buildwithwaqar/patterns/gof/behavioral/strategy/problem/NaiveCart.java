package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;

import java.util.*;

public final class NaiveCart {
  public static int total(int cents, String mode){
    if("pct".equals(mode)) return cents*90/100;
    if("flat".equals(mode)) return Math.max(0,cents-200);
    return cents/2;
  }
}
