package com.buildwithwaqar.patterns.gof.behavioral.visitor.problem;

import java.util.*;

public final class NaiveAst {
  public static String show(String kind, int a, int b){
    if("add".equals(kind)) return "("+a+"+"+b+")";
    return "("+a+"*"+b+")";
  }
}
