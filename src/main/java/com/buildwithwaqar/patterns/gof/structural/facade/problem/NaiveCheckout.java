package com.buildwithwaqar.patterns.gof.structural.facade.problem;

import java.util.*;

public final class NaiveCheckout {
  public static String place(String sku, int cents) {
    // client calls three services itself
    boolean inv = true; boolean pay = cents > 0; boolean ship = true;
    if (!inv || !pay || !ship) return "FAIL";
    return "OK:"+sku;
  }
}
