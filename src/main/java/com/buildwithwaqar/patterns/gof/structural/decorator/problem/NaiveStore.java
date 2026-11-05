package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

public final class NaiveStore {
  private String data="";
  public void write(String s){ data="enc("+s+")"; } // one hard-wired transform
  public String read(){ return data; }
}
