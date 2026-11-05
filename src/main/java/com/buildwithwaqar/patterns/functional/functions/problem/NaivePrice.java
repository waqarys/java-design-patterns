package com.buildwithwaqar.patterns.functional.functions.problem;

import java.util.*;

public final class NaivePrice {
  public static int price(int base, int pct, int flat){ return Math.max(0, base*(100-pct)/100 - flat); }
}
