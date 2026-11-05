package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;

import java.util.*;

public final class NaiveDoc {
  public String text="";
  public String backup; // single slot only
  public void save(){ backup=text; }
  public void restore(){ text=backup; }
}
