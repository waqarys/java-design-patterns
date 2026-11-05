package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;

public final class NaiveEditor {
  private String text="";
  public void write(String s){ text+=s; }
  public void undo(){ /* cannot undo */ }
  public String text(){ return text; }
}
