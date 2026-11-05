package com.buildwithwaqar.patterns.functional.kleisli.problem;

import java.util.*;

public final class NaiveAuth {
  public static String auth(String raw){ int id=Integer.parseInt(raw); return id>0? "allow:"+id: "deny"; }
}
