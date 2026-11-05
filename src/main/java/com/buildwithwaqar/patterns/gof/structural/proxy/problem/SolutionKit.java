package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

interface Image { String display(); boolean loaded(); }
final class RealImage implements Image {
  private final String path; RealImage(String path){ this.path=path; System.out.println("LOAD "+path); }
  public String display(){ return "shown:"+path; } public boolean loaded(){ return true; }
}
final class LazyImageProxy implements Image {
  private final String path; private RealImage real;
  LazyImageProxy(String path){ this.path=path; }
  public String display(){ if(real==null) real=new RealImage(path); return real.display(); }
  public boolean loaded(){ return real!=null; }
}
