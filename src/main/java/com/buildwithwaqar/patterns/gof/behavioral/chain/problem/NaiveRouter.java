package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;

public final class NaiveRouter {
  public static String route(String type){
    if("billing".equals(type)) return "billing-desk";
    if("tech".equals(type)) return "tech-desk";
    return "general-desk";
  }
}
