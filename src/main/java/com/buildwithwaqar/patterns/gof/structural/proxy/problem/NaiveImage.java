package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;

public final class NaiveImage {
  public NaiveImage(String path){ System.out.println("LOAD "+path); }
  public String display(){ return "shown"; }
}
