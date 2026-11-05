package com.buildwithwaqar.patterns.functional.either.problem;

import java.util.*;

public final class NaiveUser {
  public static String load(String id){ if(id==null) throw new RuntimeException("null"); return "user:"+id; }
}
