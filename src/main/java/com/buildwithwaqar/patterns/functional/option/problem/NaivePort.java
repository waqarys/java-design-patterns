package com.buildwithwaqar.patterns.functional.option.problem;

import java.util.*;

public final class NaivePort {
  public static Integer parse(String s){ try { int p=Integer.parseInt(s); return p>0?p:null; } catch(Exception e){ return null; } }
}
