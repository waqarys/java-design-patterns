package com.buildwithwaqar.patterns.gof.behavioral.templatemethod.problem;

import java.util.*;

public final class NaiveMine {
  public static String csv(String raw){ return "rows=" + raw.split("\n").length; }
  public static String json(String raw){ return "rows=" + raw.split("\\{", -1).length; }
}
